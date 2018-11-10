package com.ptool.db;

import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.ptool.pojo.AreaStyleTO;
import com.ptool.pojo.AreaTO;
import com.ptool.pojo.PostcodeTO;

public class DerbyAreaDAO implements IAreaDAO {

	public void saveArea(AreaTO area) {
		if(area.isNew()) {
			insertArea(area);
			insertAreaPostcodes(area.getId(),area.getPostcodes());
		}
		else {
			updateArea(area);
			deleteAreaPostcodes(area.getId());
			insertAreaPostcodes(area.getId(),area.getPostcodes());
		}

	}
	
	private void insertArea(AreaTO area) {
		String sqlInsertArea="insert into tbl_area(name,color_background,color_line,line_thickness,transparency) "
				+ "values(?,?,?,?,?)";
		Connection conn=DerbyDAOFactory.createConnection();
		PreparedStatement pstmnt=null;
		ResultSet rs=null;
		
		try {
			pstmnt=conn.prepareStatement(sqlInsertArea,Statement.RETURN_GENERATED_KEYS);
			pstmnt.setString(1, area.getName());
			pstmnt.setString(2, AreaStyleTO.toHexString(area.getStyle().getBackgroundColor()));
			pstmnt.setString(3, AreaStyleTO.toHexString(area.getStyle().getLineColor()));
			pstmnt.setDouble(4, area.getStyle().getLineThickness());
			pstmnt.setDouble(5, area.getStyle().getTransparency());
			pstmnt.executeUpdate();
			rs=pstmnt.getGeneratedKeys();
			if(rs.next()) {
				area.setId(rs.getInt(1));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			try {
				if(pstmnt!=null) {
					pstmnt.close();
				}
				conn.close();
			} 
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	private void updateArea(AreaTO area) {
		
	}
	
	private void deleteAreaPostcodes(int id) {
		
	}
	
	private void insertAreaPostcodes(int id,Set<PostcodeTO> postcodes) {
		Connection conn=DerbyDAOFactory.createConnection();
		PreparedStatement pstmnt=null;
		String sqlInsertAreaPC="insert into tbl_area_postcodes(area_id,postcode) values(?,?)";
		
		try {
			pstmnt=conn.prepareStatement(sqlInsertAreaPC);
			for(PostcodeTO pc : postcodes) {
				pstmnt.setInt(1, id);
				pstmnt.setString(2, pc.getPostcode());
				pstmnt.executeUpdate();
			}
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				if(pstmnt!=null) {
					pstmnt.close();
				}
				conn.close();
			} 
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public List<AreaTO> findAllMapAreas(int mapId) {
		Connection conn=DerbyDAOFactory.createConnection();
		PreparedStatement pstmnt=null;
		ResultSet rs=null;
		String sqlFindAllAreas="select id,map_id,name,color_background,color_line,line_thickness,transparency "
				+ "from tbl_area where map_id=?";
		List<AreaTO> areas=null;
		
		try {
			pstmnt=conn.prepareStatement(sqlFindAllAreas);
			pstmnt.setInt(1, mapId);
			rs=pstmnt.executeQuery();
			areas=new ArrayList<AreaTO>();
			while(rs.next()) {
				areas.add(createArea(rs));
			}
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return areas;
	}

	public AreaTO findAreaById(int id) {
		Connection conn=DerbyDAOFactory.createConnection();
		PreparedStatement pstmnt=null;
		String sqlFindAreaById="select id,map_id,name,color_background,color_line,line_thickness,transparency "
				+ "from tbl_area where id=?";
		ResultSet rs=null;
		AreaTO area=null;
		try {
			pstmnt=conn.prepareStatement(sqlFindAreaById);
			pstmnt.setInt(1, id);
			rs=pstmnt.executeQuery();
			if(rs.next()) {
				area=createArea(rs);
			}
		} 
		catch (SQLException e) {
			
			e.printStackTrace();
		}
		finally {
			try {
				rs.close();
				pstmnt.close();
				conn.close();
			} 
			catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		return area;
	}
	
	public void deleteArea(int id) {
		// TODO Auto-generated method stub
		
	}
	
	private AreaTO createArea(ResultSet rs) throws SQLException {
		AreaTO area=new AreaTO();
		area.setId(rs.getInt("id"));
		area.setMapId(rs.getInt("map_id"));
		area.setName(rs.getString("name"));
		AreaStyleTO style=new AreaStyleTO();
		style.setBackgroundColor(Color.decode(rs.getString("color_background")));
		style.setLineColor(Color.decode(rs.getString("color_line")));
		style.setTransparency(rs.getDouble("line_thickness"));
		style.setLineThickness(rs.getDouble("line_thickness"));
		area.setStyle(style);
		return area;
	}

}

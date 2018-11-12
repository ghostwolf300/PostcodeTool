package com.ptool.db;

import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ptool.pojo.AreaStyleTO;
import com.ptool.pojo.AreaTO;
import com.ptool.pojo.PostcodeTO;

public class DerbyAreaDAO implements IAreaDAO {

	public void saveArea(AreaTO area) {
		Connection conn=DerbyDAOFactory.createConnection();
		System.out.println("AreaDAO new area="+area.isNew());
		try {
			if(area.isNew()) {
				insertArea(area,conn);
				if(area.getPostcodes()!=null && area.getPostcodes().size()>0) {
					insertAreaPostcodes(area.getId(),area.getPostcodes(),conn);
				}
			}
			else {
				updateArea(area,conn);
				deleteAreaPostcodes(area.getId(),conn);
				System.out.println("Updating area postcode size: "+area.getPostcodes().size());
				if(area.getPostcodes()!=null && area.getPostcodes().size()>0) {
					insertAreaPostcodes(area.getId(),area.getPostcodes(),conn);
				}
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				conn.close();
			} 
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	private void insertArea(AreaTO area,Connection conn) throws SQLException {
		String sqlInsertArea="insert into tbl_area(name,color_background,color_line,line_thickness,transparency) "
				+ "values(?,?,?,?,?)";
		
		PreparedStatement pstmnt=conn.prepareStatement(sqlInsertArea,Statement.RETURN_GENERATED_KEYS);
		pstmnt.setString(1, area.getName());
		pstmnt.setString(2, AreaStyleTO.toHexString(area.getStyle().getBackgroundColor()));
		pstmnt.setString(3, AreaStyleTO.toHexString(area.getStyle().getLineColor()));
		pstmnt.setDouble(4, area.getStyle().getLineThickness());
		pstmnt.setDouble(5, area.getStyle().getTransparency());
		pstmnt.executeUpdate();
		ResultSet rs=pstmnt.getGeneratedKeys();
		if(rs.next()) {
			area.setId(rs.getInt(1));
		}
		rs.close();
		pstmnt.close();
			
	}
	
	private void updateArea(AreaTO area,Connection conn) throws SQLException {
		String sqlUpdateArea="update tbl_area set name=?,color_background=?,color_line=?,transparency=?,line_thickness=? where id=?";
		PreparedStatement pstmnt=conn.prepareStatement(sqlUpdateArea);
		pstmnt.setString(1, area.getName());
		pstmnt.setString(2, AreaStyleTO.toHexString(area.getStyle().getBackgroundColor()));
		pstmnt.setString(3, AreaStyleTO.toHexString(area.getStyle().getLineColor()));
		pstmnt.setDouble(4, area.getStyle().getTransparency());
		pstmnt.setDouble(5, area.getStyle().getLineThickness());
		pstmnt.setInt(6, area.getId());
		pstmnt.executeUpdate();
		pstmnt.close();
	}
	
	private void deleteAreaPostcodes(int areaId,Connection conn) throws SQLException {
		
		String sqlDelAreaPostcodes="delete from tbl_area_postcodes where area_id=?";
		PreparedStatement pstmnt=conn.prepareStatement(sqlDelAreaPostcodes);
		pstmnt.setInt(1, areaId);
		pstmnt.executeUpdate();
		pstmnt.close();
		
	}
	
	private void insertAreaPostcodes(int id,Set<PostcodeTO> postcodes,Connection conn) throws SQLException {
		
		String sqlInsertAreaPC="insert into tbl_area_postcodes(area_id,postcode) values(?,?)";
		PreparedStatement pstmnt=conn.prepareStatement(sqlInsertAreaPC);
		
		for(PostcodeTO pc : postcodes) {
			pstmnt.setInt(1, id);
			pstmnt.setString(2, pc.getPostcode());
			pstmnt.executeUpdate();
		}
		
		pstmnt.close();
		
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
				AreaTO area=createArea(rs);
				//TODO: use postcodeDAO to retrieve postcode data
				//addPostcodesToArea(area,conn);
				areas.add(area);
			}
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			
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
	
	private void addPostcodesToArea(AreaTO area,Connection conn) throws SQLException {
		String sqlFindPostcodes="select b.postcode,b.name,b.map_id from tbl_area_postcodes a join tbl_postcode b on a.postcode=b.postcode and a.map_id=b.map_id where a.area_id=?";
		PreparedStatement pstmnt=conn.prepareStatement(sqlFindPostcodes);
		pstmnt.setInt(1, area.getId());
		ResultSet rs=pstmnt.executeQuery();
		Set<PostcodeTO> postcodes=new HashSet<PostcodeTO>();
		while(rs.next()) {
			postcodes.add(createPostcode(rs));
		}
		area.setPostcodes(postcodes);
	}
	
	private PostcodeTO createPostcode(ResultSet rs) throws SQLException {
		PostcodeTO pc=new PostcodeTO();
		pc.setPostcode(rs.getString("postcode"));
		pc.setName(rs.getString("name"));
		pc.setMapId(rs.getInt("map_id"));
		return pc;
	}
	
	public void removeArea(int id) {
		Connection conn=DerbyDAOFactory.createConnection();
		PreparedStatement pstmnt=null;
		String sqlDeleteArea="delete from tbl_area where id=?";
		
		try {
			pstmnt=conn.prepareStatement(sqlDeleteArea);
			pstmnt.setInt(1, id);
			pstmnt.executeUpdate();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				pstmnt.close();
				conn.close();
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
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

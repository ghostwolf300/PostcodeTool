package com.ptool.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ptool.pojo.MapDataTO;

public class DerbyMapDAO implements IMapDAO {

	public MapDataTO findMapById(int id) {
		
		String sqlFindMapById="select id,name,min_x,min_y,max_x,max_y,crs from tbl_map where id=?";
		Connection conn=DerbyDAOFactory2.createConnection();
		PreparedStatement pstmnt=null;
		ResultSet rs=null;
		MapDataTO mapData=null;
		
		try {
			pstmnt=conn.prepareStatement(sqlFindMapById);
			pstmnt.setInt(1, id);
			rs=pstmnt.executeQuery();
			if(rs.next()) {
				mapData=createMapData(rs);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(rs!=null) {
					rs.close();
				}
				if(pstmnt!=null) {
					pstmnt.close();
				}
				conn.close();
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return mapData;
	}
	
	public List<MapDataTO> findAllMaps() {
		String sqlFindAllMaps="select id,name,min_x,min_y,max_x,max_y,crs from tbl_map order by name";
		Connection conn=DerbyDAOFactory2.createConnection();
		PreparedStatement pstmnt=null;
		ResultSet rs=null;
		List<MapDataTO> maps=new ArrayList<MapDataTO>();
		
		try {
			pstmnt=conn.prepareStatement(sqlFindAllMaps);
			rs=pstmnt.executeQuery();
			while(rs.next()) {
				maps.add(createMapData(rs));
			}
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				rs.close();
				pstmnt.close();
				conn.close();
			} 
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return maps;
	}

	private MapDataTO createMapData(ResultSet rs) throws SQLException {
		MapDataTO mapData=new MapDataTO();
		mapData.setId(rs.getInt("id"));
		mapData.setName(rs.getString("name"));
		mapData.setMinX(rs.getDouble("min_x"));
		mapData.setMinY(rs.getDouble("min_y"));
		mapData.setMaxX(rs.getDouble("max_x"));
		mapData.setMaxY(rs.getDouble("max_y"));
		mapData.setCrs(rs.getString("crs"));
		return mapData;
	}

	public void saveMap(MapDataTO mapData) {
		
		Connection conn=DerbyDAOFactory2.createConnection();
		
		try {
			if(mapData.isNew()) {
				insertMap(mapData,conn);
			}
			else {
				updateMap(mapData,conn);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				conn.close();
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void insertMap(MapDataTO mapData,Connection conn) throws SQLException {
		
		String sqlInsert="insert into tbl_map (name,min_x,min_y,max_x,max_y,crs) values(?,?,?,?,?,?)";
		PreparedStatement pstmnt=conn.prepareStatement(sqlInsert,Statement.RETURN_GENERATED_KEYS);
		ResultSet rsKey=null;
		
		pstmnt.setString(1, mapData.getName());
		pstmnt.setDouble(2, mapData.getMinX());
		pstmnt.setDouble(3, mapData.getMinY());
		pstmnt.setDouble(4, mapData.getMaxX());
		pstmnt.setDouble(5, mapData.getMaxY());
		pstmnt.setString(6, mapData.getCrs());
		pstmnt.executeUpdate();
		rsKey=pstmnt.getGeneratedKeys();
		if(rsKey.next()) {
			int id=rsKey.getInt(1);
			mapData.setId(id);
		}
		 
		rsKey.close();
		pstmnt.close();
			
	}
	
	private void updateMap(MapDataTO mapData,Connection conn) throws SQLException{
		
		String sqlUpdate="update tbl_collection set name=?,min_x=?,min_y=?,max_x=?,max_y=?,crs=? where id=?";
		PreparedStatement pstmnt=conn.prepareStatement(sqlUpdate);
		
		pstmnt.setString(1, mapData.getName());
		pstmnt.setDouble(2, mapData.getMinX());
		pstmnt.setDouble(3, mapData.getMinY());
		pstmnt.setDouble(4, mapData.getMaxX());
		pstmnt.setDouble(5, mapData.getMaxY());
		pstmnt.setString(6, mapData.getCrs());
		pstmnt.setInt(7, mapData.getId());
		
		pstmnt.executeUpdate();
		pstmnt.close();
	}

}
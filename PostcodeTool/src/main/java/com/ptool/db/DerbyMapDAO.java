package com.ptool.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ptool.pojo.MapDataTO;

public class DerbyMapDAO implements IMapDAO {

	public MapDataTO findMapById(int id) {
		
		String sqlFindMapById="select id,name,min_x,min_y,max_x,max_y,crs from tbl_map where id=?";
		Connection conn=DerbyDAOFactory.createConnection();
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
		Connection conn=DerbyDAOFactory.createConnection();
	}

}

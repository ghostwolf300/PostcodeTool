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

import com.ptool.pojo.CollectionStyleTO;
import com.ptool.pojo.CollectionTO;
import com.ptool.pojo.MapAreaTO;

public class DerbyCollectionDAO implements ICollectionDAO {

	public void saveCollection(CollectionTO collection) {
		
		Connection conn=DerbyDAOFactory2.createConnection();
		
		System.out.println("CollectionDAO new collection="+collection.isNew());
		try {
			if(collection.isNew()) {
				insertCollection(collection,conn);
				if(collection.getMapAreas()!=null && collection.getMapAreas().size()>0) {
					insertCollectionMapAreas(collection.getId(),collection.getMapAreas(),conn);
				}
			}
			else {
				updateCollection(collection,conn);
				deleteCollectionMapAreas(collection.getId(),conn);
				System.out.println("Updating area postcode size: "+collection.getMapAreas().size());
				if(collection.getMapAreas()!=null && collection.getMapAreas().size()>0) {
					insertCollectionMapAreas(collection.getId(),collection.getMapAreas(),conn);
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
	
	private void insertCollection(CollectionTO collection,Connection conn) throws SQLException {
		String sqlInsert="insert into tbl_collection(map_id,name,color_background,color_line,line_thickness,transparency) "
				+ "values(?,?,?,?,?,?)";
		
		PreparedStatement pstmnt=conn.prepareStatement(sqlInsert,Statement.RETURN_GENERATED_KEYS);
		pstmnt.setInt(1, collection.getMapId());
		pstmnt.setString(2, collection.getName());
		pstmnt.setString(3, CollectionStyleTO.toHexString(collection.getStyle().getBackgroundColor()));
		pstmnt.setString(4, CollectionStyleTO.toHexString(collection.getStyle().getLineColor()));
		pstmnt.setDouble(5, collection.getStyle().getLineThickness());
		pstmnt.setDouble(6, collection.getStyle().getTransparency());
		pstmnt.executeUpdate();
		ResultSet rs=pstmnt.getGeneratedKeys();
		if(rs.next()) {
			collection.setId(rs.getInt(1));
		}
		rs.close();
		pstmnt.close();
			
	}
	
	private void insertCollectionMapAreas(int id,Set<MapAreaTO> mapAreas,Connection conn) throws SQLException {
		
		String sqlInsert="insert into tbl_collection_areas(collection_id,area_id) values(?,?)";
		PreparedStatement pstmnt=conn.prepareStatement(sqlInsert);
		
		for(MapAreaTO ma : mapAreas) {
			pstmnt.setInt(1, id);
			pstmnt.setInt(2, ma.getId());
			pstmnt.executeUpdate();
		}
		
		pstmnt.close();
		
	}

	private void updateCollection(CollectionTO collection,Connection conn) throws SQLException {
		String sqlUpdate="update tbl_collection set name=?,color_background=?,color_line=?,transparency=?,line_thickness=? where id=?";
		PreparedStatement pstmnt=conn.prepareStatement(sqlUpdate);
		pstmnt.setString(1, collection.getName());
		pstmnt.setString(2, CollectionStyleTO.toHexString(collection.getStyle().getBackgroundColor()));
		pstmnt.setString(3, CollectionStyleTO.toHexString(collection.getStyle().getLineColor()));
		pstmnt.setDouble(4, collection.getStyle().getTransparency());
		pstmnt.setDouble(5, collection.getStyle().getLineThickness());
		pstmnt.setInt(6, collection.getId());
		pstmnt.executeUpdate();
		pstmnt.close();
	}
	
	private void deleteCollectionMapAreas(int id,Connection conn) throws SQLException {
		
		String sqlDelete="delete from tbl_collection_areas where collection_id=?";
		PreparedStatement pstmnt=conn.prepareStatement(sqlDelete);
		pstmnt.setInt(1, id);
		pstmnt.executeUpdate();
		pstmnt.close();
		
	}
	
	public List<CollectionTO> findCollectionsByMapId(int mapId) {
		Connection conn=DerbyDAOFactory2.createConnection();
		PreparedStatement pstmnt=null;
		ResultSet rs=null;
		String sqlFindCollections="select id,map_id,name,color_background,color_line,line_thickness,transparency "
				+ "from tbl_collection where map_id=?";
		List<CollectionTO> collections=null;
		
		try {
			pstmnt=conn.prepareStatement(sqlFindCollections);
			pstmnt.setInt(1, mapId);
			rs=pstmnt.executeQuery();
			collections=new ArrayList<CollectionTO>();
			while(rs.next()) {
				CollectionTO coll=createCollection(rs);
				//Use postcodeDAO to retrieve postcode data
				collections.add(coll);
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
		
		return collections;
	}

	public CollectionTO findCollectionById(int id) {
		Connection conn=DerbyDAOFactory2.createConnection();
		PreparedStatement pstmnt=null;
		String sqlFindCollection="select id,map_id,name,color_background,color_line,line_thickness,transparency "
				+ "from tbl_collection where id=?";
		ResultSet rs=null;
		CollectionTO collection=null;
		try {
			pstmnt=conn.prepareStatement(sqlFindCollection);
			pstmnt.setInt(1, id);
			rs=pstmnt.executeQuery();
			if(rs.next()) {
				collection=createCollection(rs);
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
		
		return collection;
	}
	
	private CollectionTO createCollection(ResultSet rs) throws SQLException {
		CollectionTO collection=new CollectionTO();
		collection.setId(rs.getInt("id"));
		collection.setMapId(rs.getInt("map_id"));
		collection.setName(rs.getString("name"));
		CollectionStyleTO style=new CollectionStyleTO();
		style.setBackgroundColor(Color.decode(rs.getString("color_background")));
		style.setLineColor(Color.decode(rs.getString("color_line")));
		style.setTransparency(rs.getDouble("transparency"));
		style.setLineThickness(rs.getDouble("line_thickness"));
		collection.setStyle(style);
		return collection;
	}

	public void removeCollection(int id) {
		Connection conn=DerbyDAOFactory2.createConnection();
		PreparedStatement pstmnt=null;
		String sqlDelete="delete from tbl_collection where id=?";
		
		try {
			pstmnt=conn.prepareStatement(sqlDelete);
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

}

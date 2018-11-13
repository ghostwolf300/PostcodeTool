package com.ptool.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ptool.pojo.CoordinateTO;
import com.ptool.pojo.PolygonTO;
import com.ptool.pojo.PostcodeTO;
import com.ptool.pojo.RingTO;

public class DerbyPostcodeDAO2 implements IPostcodeDAO {

	public void createDB() {
		Connection conn=DerbyDAOFactory.createConnection();
		try {
			conn.close();
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void savePostcodes(List<PostcodeTO> postcodes) {
		String sqlInsertPostCode="insert into tbl_area(map_id,name_1,name_2) values(?,?,?)";
		PreparedStatement pstmnt=null;
		Connection conn=DerbyDAOFactory2.createConnection();
		
		try {
			pstmnt=conn.prepareStatement(sqlInsertPostCode);
			for(PostcodeTO pc : postcodes) {
				pstmnt.setInt(1, pc.getMapId());
				pstmnt.setString(2, pc.getPostcode());
				pstmnt.setString(3, pc.getName());
				pstmnt.executeUpdate();
				
				for(PolygonTO polygon : pc.getPolygons()) {
					savePolygon(polygon,conn);
					savePostcodePolygon(pc.getMapId(),pc.getPostcode(),polygon.getId(),conn);
				}
			}
			
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if(pstmnt!=null) {
				try {
					pstmnt.close();
				} 
				catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				conn.close();
			} 
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	private void savePolygon(PolygonTO polygon,Connection conn) throws SQLException{
		int id=-1;
		String sqlInsertPolygon="insert into tbl_polygon(geometry_type) values(?)";
		PreparedStatement pstmnt=null;
		ResultSet rsKey=null;
		
		pstmnt=conn.prepareStatement(sqlInsertPolygon,Statement.RETURN_GENERATED_KEYS);
		pstmnt.setString(1, "Polygon");
		pstmnt.executeUpdate();
		rsKey=pstmnt.getGeneratedKeys();
		if(rsKey.next()) {
			id=rsKey.getInt(1);
			polygon.setId(id);
		}

		for(RingTO ring : polygon.getRings()) {
			saveRing(ring,conn);
			savePolygonRing(polygon.getId(),ring.getId(),conn);
		}
		 
		rsKey.close();
		pstmnt.close();
		
	}
	
	private void savePolygonRing(int polygonId,int ringId,Connection conn) throws SQLException {
		PreparedStatement pstmnt=null;
		String sqlInsertPolygonRing="insert into tbl_polygon_rings(polygon_id,ring_id) values(?,?)";
		
		pstmnt=conn.prepareStatement(sqlInsertPolygonRing);
		pstmnt.setInt(1, polygonId);
		pstmnt.setInt(2, ringId);
		pstmnt.executeUpdate();
		
		pstmnt.close();
	}
	
	private void savePostcodePolygon(int mapId,String postcode,int polygonId,Connection conn) throws SQLException {
		PreparedStatement pstmnt=null;
		String sqlInsertPostcodePolygon="insert into tbl_postcode_polygons(map_id,postcode,polygon_id) values(?,?,?)";
		
		pstmnt=conn.prepareStatement(sqlInsertPostcodePolygon);
		pstmnt.setInt(1, mapId);
		pstmnt.setString(2, postcode);
		pstmnt.setInt(3, polygonId);
		pstmnt.executeUpdate();
		
		pstmnt.close();
		
	}
	
	private void saveRing(RingTO ring,Connection conn) throws SQLException {
		
		PreparedStatement pstmnt=null;
		String sqlSaveRing="insert into tbl_ring(ring_type) values(?)";
		ResultSet rsKey=null;
		
		pstmnt=conn.prepareStatement(sqlSaveRing, Statement.RETURN_GENERATED_KEYS);
		pstmnt.setInt(1, ring.getRingType());
		pstmnt.executeUpdate();
		rsKey=pstmnt.getGeneratedKeys();
		
		if(rsKey.next()) {
			ring.setId(rsKey.getInt(1));
		}
		
		for(CoordinateTO coordinates : ring.getCoordinates()) {
			coordinates.setRingId(ring.getId());
			saveCoordinates(coordinates,conn);
		}
		
		rsKey.close();
		pstmnt.close();
			
	}
	
	private void saveCoordinates(CoordinateTO coordinates,Connection conn) throws SQLException{
		PreparedStatement pstmnt=null;
		String sqlInsertCoordinates="insert into tbl_coordinates(ring_id,order_num,x,y) values(?,?,?,?)";
		
		pstmnt=conn.prepareStatement(sqlInsertCoordinates);
		pstmnt.setInt(1, coordinates.getRingId());
		pstmnt.setInt(2, coordinates.getOrderNum());
		pstmnt.setDouble(3, coordinates.getX());
		pstmnt.setDouble(4, coordinates.getY());
		pstmnt.executeUpdate();
		
		pstmnt.close();
		
	}

	public void clearTables() {
		Connection conn=null;
		
		PreparedStatement pstmntDelete=null;
		
		String sqlDelPostcodes="delete from tbl_postcode";
		String sqlDelPolygons="delete from tbl_polygon";
		String sqlDelPostcodePolygons="delete from tbl_postcode_polygons";
		String sqlDelRings="delete from tbl_ring";
		String sqlDelPolygonRings="delete from tbl_polygon_rings";
		String sqlDelCoordinates="delete from tbl_coordinates";
		
		conn=DerbyDAOFactory.createConnection();
		
		try {
			pstmntDelete=conn.prepareStatement(sqlDelPostcodes);
			pstmntDelete.executeUpdate();
			pstmntDelete.close();
			pstmntDelete=conn.prepareStatement(sqlDelPolygons);
			pstmntDelete.executeUpdate();
			pstmntDelete.close();
			pstmntDelete=conn.prepareStatement(sqlDelPostcodePolygons);
			pstmntDelete.executeUpdate();
			pstmntDelete.close();
			pstmntDelete=conn.prepareStatement(sqlDelRings);
			pstmntDelete.executeUpdate();
			pstmntDelete.close();
			pstmntDelete=conn.prepareStatement(sqlDelPolygonRings);
			pstmntDelete.executeUpdate();
			pstmntDelete.close();
			pstmntDelete=conn.prepareStatement(sqlDelCoordinates);
			pstmntDelete.executeUpdate();
			pstmntDelete.close();
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
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

	public PostcodeTO findPostcode(String postcode,int mapId) {
		
		String sqlGetPostcode="select postcode,name from tbl_postcode where postcode=? and map_id=?";
		
		Connection conn=DerbyDAOFactory.createConnection();
		PreparedStatement pstmnt=null;
		ResultSet rsPostcode=null;
		
		PostcodeTO pc=null;
		
		try {
			pstmnt=conn.prepareStatement(sqlGetPostcode);
			pstmnt.setString(1, postcode);
			pstmnt.setInt(2, mapId);
			rsPostcode=pstmnt.executeQuery();
			if(rsPostcode.next()) {
				pc=new PostcodeTO();
				pc.setMapId(mapId);
				pc.setPostcode(rsPostcode.getString("postcode"));
				pc.setName(rsPostcode.getString("name"));
				pc.setPolygons(getPolygons(pc.getPostcode(),mapId,conn));
			}
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
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
		
		return pc;
	}
	
	private List<PolygonTO> getPolygons(String postcode,int mapId,Connection conn) throws SQLException{
		List<PolygonTO> polygons=new ArrayList<PolygonTO>();
		String sqlGetPolygons="select a.postcode,a.polygon_id,b.geometry_type "
				+"from tbl_postcode_polygons a join tbl_polygon b on a.polygon_id=b.id "
				+"where a.postcode=? and a.map_id=? order by a.polygon_id";
		ResultSet rsPolygons=null;
		PreparedStatement pstmnt=conn.prepareStatement(sqlGetPolygons);
		pstmnt.setString(1, postcode);
		pstmnt.setInt(2, mapId);
		rsPolygons=pstmnt.executeQuery();
		while(rsPolygons.next()) {
			polygons.add(createPolygon(rsPolygons));
		}
		
		for(PolygonTO p : polygons) {
			p.setRings(getRings(p.getId(),conn));
		}
		
		pstmnt.close();
		rsPolygons.close();
		
		return polygons;
	}
	
	private PolygonTO createPolygon(ResultSet rs) throws SQLException {
		PolygonTO p=new PolygonTO();
		p.setId(rs.getInt("polygon_id"));
		return p;
	}
	
	private List<RingTO> getRings(int polygonId,Connection conn) throws SQLException{
		List<RingTO> rings=new ArrayList<RingTO>();
		String sqlGetRings="select a.polygon_id,a.ring_id,b.ring_type " 
				+"from tbl_polygon_rings a join tbl_ring b on a.ring_id=b.id "
				+"where a.polygon_id=? order by a.ring_id";
		ResultSet rsRings=null;
		PreparedStatement pstmnt=conn.prepareStatement(sqlGetRings);
		pstmnt.setInt(1, polygonId);
		rsRings=pstmnt.executeQuery();
		while(rsRings.next()) {
			rings.add(createRing(rsRings));
		}
		
		for(RingTO ring : rings) {
			ring.setCoordinates(getCoordinates(ring.getId(),conn));
		}
		
		rsRings.close();
		pstmnt.close();
		
		return rings;
	}
	
	private RingTO createRing(ResultSet rs) throws SQLException{
		RingTO ring=new RingTO();
		ring.setId(rs.getInt("ring_id"));
		ring.setRingType(rs.getInt("ring_type"));
		return ring;
	}
	
	private List<CoordinateTO> getCoordinates(int ringId,Connection conn) throws SQLException{
		List<CoordinateTO> coordinates=new ArrayList<CoordinateTO>();
		String sqlGetCoordinates="select ring_id,order_num,x,y from tbl_coordinates "
				+ "where ring_id=? order by order_num";
		PreparedStatement pstmnt=conn.prepareStatement(sqlGetCoordinates);
		pstmnt.setInt(1, ringId);
		ResultSet rsCoordinates=null;
		rsCoordinates=pstmnt.executeQuery();
		
		while(rsCoordinates.next()) {
			coordinates.add(createCoordinates(rsCoordinates));
		}
		
		rsCoordinates.close();
		pstmnt.close();
		
		return coordinates;
	}
	
	private CoordinateTO createCoordinates(ResultSet rs) throws SQLException {
		CoordinateTO coord=new CoordinateTO();
		coord.setRingId(rs.getInt("ring_id"));
		coord.setOrderNum(rs.getInt("order_num"));
		coord.setX(rs.getDouble("x"));
		coord.setY(rs.getDouble("y"));
		return coord;
		
	}

	public List<PostcodeTO> findAllPostcodes(int mapId) {
		String sqlGetAllPostcodes="select map_id,postcode,name from tbl_postcode where map_id=? order by name";
		
		Connection conn=DerbyDAOFactory.createConnection();
		PreparedStatement pstmnt=null;
		ResultSet rsPostcodes=null;
		
		PostcodeTO pc=null;
		List<PostcodeTO> postcodes=new ArrayList<PostcodeTO>();
		
		try {
			pstmnt=conn.prepareStatement(sqlGetAllPostcodes);
			pstmnt.setInt(1, mapId);
			rsPostcodes=pstmnt.executeQuery();
			while(rsPostcodes.next()) {
				pc=new PostcodeTO();
				pc.setMapId(mapId);
				pc.setPostcode(rsPostcodes.getString("postcode"));
				pc.setName(rsPostcodes.getString("name"));
				pc.setPolygons(getPolygons(pc.getPostcode(),mapId,conn));
				postcodes.add(pc);
			}
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				pstmnt.close();
				conn.close();
			} 
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return postcodes;
	}

	public List<PostcodeTO> findPostcodesByAreaId(int areaId,int mapId) {
		String sqlFindPostcodesByAreaId="select a.postcode,a.name from tbl_postcode a "
				+ "join tbl_area_postcodes b on a.postcode=b.postcode and a.map_id=b.map_id where b.area_id=?";
		
		Connection conn=DerbyDAOFactory.createConnection();
		PreparedStatement pstmnt=null;
		ResultSet rsPostcodes=null;
		
		PostcodeTO pc=null;
		List<PostcodeTO> postcodes=new ArrayList<PostcodeTO>();
		
		try {
			pstmnt=conn.prepareStatement(sqlFindPostcodesByAreaId);
			pstmnt.setInt(1, areaId);
			rsPostcodes=pstmnt.executeQuery();
			while(rsPostcodes.next()) {
				pc=new PostcodeTO();
				pc.setMapId(mapId);
				pc.setPostcode(rsPostcodes.getString("postcode"));
				pc.setName(rsPostcodes.getString("name"));
				pc.setPolygons(getPolygons(pc.getPostcode(),mapId,conn));
				postcodes.add(pc);
			}
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				pstmnt.close();
				conn.close();
			} 
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		return postcodes;
	}

}

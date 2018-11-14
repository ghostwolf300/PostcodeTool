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
import com.ptool.pojo.MapAreaTO;
import com.ptool.pojo.RingTO;

public class DerbyMapAreaDAO implements IMapAreaDAO {

	public void createDB() {
		Connection conn=DerbyDAOFactory2.createConnection();
		try {
			conn.close();
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void saveMapAreas(List<MapAreaTO> postcodes) {
		String sqlInsertPostCode="insert into tbl_area(map_id,name_1,name_2,name_3,name_4) values(?,?,?,?,?)";
		PreparedStatement pstmnt=null;
		Connection conn=DerbyDAOFactory2.createConnection();
		ResultSet rsKey=null;
		int id=-1;
		
		try {
			pstmnt=conn.prepareStatement(sqlInsertPostCode,Statement.RETURN_GENERATED_KEYS);
			for(MapAreaTO pc : postcodes) {
				pstmnt.setInt(1, pc.getMapId());
				pstmnt.setString(2, pc.getName1());
				pstmnt.setString(3, pc.getName2());
				pstmnt.setString(4, pc.getName3());
				pstmnt.setString(5, pc.getName4());
				pstmnt.executeUpdate();
				rsKey=pstmnt.getGeneratedKeys();
				if(rsKey.next()) {
					id=rsKey.getInt(1);
					pc.setId(id);
				}
				
				for(PolygonTO polygon : pc.getPolygons()) {
					polygon.setMapAreaId(pc.getId());
					savePolygon(polygon,conn);
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
					rsKey.close();
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
		String sqlInsertPolygon="insert into tbl_polygon(area_id) values(?)";
		PreparedStatement pstmnt=null;
		ResultSet rsKey=null;
		
		pstmnt=conn.prepareStatement(sqlInsertPolygon,Statement.RETURN_GENERATED_KEYS);
		pstmnt.setInt(1, polygon.getMapAreaId());
		pstmnt.executeUpdate();
		rsKey=pstmnt.getGeneratedKeys();
		if(rsKey.next()) {
			id=rsKey.getInt(1);
			polygon.setId(id);
		}

		for(RingTO ring : polygon.getRings()) {
			ring.setPolygonId(polygon.getId());
			saveRing(ring,conn);
		}
		 
		rsKey.close();
		pstmnt.close();
		
	}
	
	private void saveRing(RingTO ring,Connection conn) throws SQLException {
		
		PreparedStatement pstmnt=null;
		String sqlSaveRing="insert into tbl_ring(polygon_id,ring_type) values(?,?)";
		ResultSet rsKey=null;
		
		pstmnt=conn.prepareStatement(sqlSaveRing, Statement.RETURN_GENERATED_KEYS);
		pstmnt.setInt(1, ring.getPolygonId());
		pstmnt.setInt(2, ring.getRingType());
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
		
		String sqlDelPostcodes="delete from tbl_area";
		String sqlDelPolygons="delete from tbl_polygon";
		String sqlDelRings="delete from tbl_ring";
		String sqlDelCoordinates="delete from tbl_coordinates";
		
		conn=DerbyDAOFactory2.createConnection();
		
		try {
			pstmntDelete=conn.prepareStatement(sqlDelPostcodes);
			pstmntDelete.executeUpdate();
			pstmntDelete.close();
			pstmntDelete=conn.prepareStatement(sqlDelPolygons);
			pstmntDelete.executeUpdate();
			pstmntDelete.close();
			pstmntDelete=conn.prepareStatement(sqlDelRings);
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

	public MapAreaTO findMapArea(int id) {
		
		String sqlGetPostcode="select id,map_id,name_1,name_2,name_3,name_4 from tbl_area where id=?";
		
		Connection conn=DerbyDAOFactory2.createConnection();
		PreparedStatement pstmnt=null;
		ResultSet rsPostcode=null;
		
		MapAreaTO pc=null;
		
		try {
			pstmnt=conn.prepareStatement(sqlGetPostcode);
			pstmnt.setInt(1, id);
			rsPostcode=pstmnt.executeQuery();
			if(rsPostcode.next()) {
				pc=createMapArea(rsPostcode);
				pc.setPolygons(getPolygons(pc.getId(),conn));
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
	
	public List<MapAreaTO> findAllMapAreas(int mapId) {
		String sqlFindMapAreas="select id,map_id,name_1,name_2,name_3,name_4 from tbl_area where map_id=? order by name_2";
		
		Connection conn=DerbyDAOFactory2.createConnection();
		PreparedStatement pstmnt=null;
		ResultSet rsMapAreas=null;
		
		MapAreaTO pc=null;
		List<MapAreaTO> postcodes=new ArrayList<MapAreaTO>();
		
		try {
			pstmnt=conn.prepareStatement(sqlFindMapAreas);
			pstmnt.setInt(1, mapId);
			rsMapAreas=pstmnt.executeQuery();
			while(rsMapAreas.next()) {
				pc=createMapArea(rsMapAreas);
				pc.setPolygons(getPolygons(pc.getId(),conn));
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

	public List<MapAreaTO> findMapAreasByCollectionId(int collectionId) {
		String sqlFindPostcodesByAreaId="select a.id,a.name_1,a.name_2,a.name_3,a.name_4 from tbl_area a "
				+ "join tbl_collection_areas b on a.id=b.area_id where b.collection_id=?";
		
		Connection conn=DerbyDAOFactory2.createConnection();
		PreparedStatement pstmnt=null;
		ResultSet rsPostcodes=null;
		
		MapAreaTO pc=null;
		List<MapAreaTO> postcodes=new ArrayList<MapAreaTO>();
		
		try {
			pstmnt=conn.prepareStatement(sqlFindPostcodesByAreaId);
			pstmnt.setInt(1, collectionId);
			rsPostcodes=pstmnt.executeQuery();
			while(rsPostcodes.next()) {
				pc=createMapArea(rsPostcodes);
				pc.setPolygons(getPolygons(pc.getId(),conn));
				postcodes.add(pc);
			}
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				rsPostcodes.close();
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

	private MapAreaTO createMapArea(ResultSet rs) throws SQLException{
		MapAreaTO area=new MapAreaTO();
		area.setId(rs.getInt("id"));
		area.setMapId(rs.getInt("map_id"));
		area.setName1(rs.getString("name_1"));
		area.setName2(rs.getString("name_2"));
		area.setName3(rs.getString("name_3"));
		area.setName4(rs.getString("name_4"));
		return area;
	}

	private PolygonTO createPolygon(ResultSet rs) throws SQLException {
		PolygonTO p=new PolygonTO();
		p.setId(rs.getInt("id"));
		p.setMapAreaId(rs.getInt("area_id"));
		return p;
	}

	private RingTO createRing(ResultSet rs) throws SQLException{
		RingTO ring=new RingTO();
		ring.setId(rs.getInt("id"));
		ring.setPolygonId(rs.getInt("polygon_id"));
		ring.setRingType(rs.getInt("ring_type"));
		return ring;
	}

	private CoordinateTO createCoordinates(ResultSet rs) throws SQLException {
		CoordinateTO coord=new CoordinateTO();
		coord.setRingId(rs.getInt("ring_id"));
		coord.setOrderNum(rs.getInt("order_num"));
		coord.setX(rs.getDouble("x"));
		coord.setY(rs.getDouble("y"));
		return coord;
		
	}

	private List<PolygonTO> getPolygons(int mapAreaId,Connection conn) throws SQLException{
		List<PolygonTO> polygons=new ArrayList<PolygonTO>();
		String sqlGetPolygons="select id,area_id from tbl_polygon where area_id=?";
		ResultSet rsPolygons=null;
		PreparedStatement pstmnt=conn.prepareStatement(sqlGetPolygons);
		pstmnt.setInt(1, mapAreaId);
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
	
	private List<RingTO> getRings(int polygonId,Connection conn) throws SQLException{
		List<RingTO> rings=new ArrayList<RingTO>();
		String sqlGetRings="select id,polygon_id,ring_type from tbl_ring where polygon_id=?";
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
	
	public MapAreaTO findMapArea(String postcode, int mapId) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<MapAreaTO> findMapAreasByCollectionId(int areaId, int mapId) {
		// TODO Auto-generated method stub
		return null;
	}

}

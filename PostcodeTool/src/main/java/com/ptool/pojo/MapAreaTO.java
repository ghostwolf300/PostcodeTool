package com.ptool.pojo;

import java.util.ArrayList;
import java.util.List;

import org.locationtech.jts.geom.Polygon;

public class MapAreaTO {
	
	private int id=-1;
	private int mapId=-1;
	private String name1=null;
	private String name2=null;
	private String name3=null;
	private String name4=null;
	private List<PolygonTO> polygons=null;
	
	public MapAreaTO() {
		
	}
	
	public MapAreaTO(int mapId,String name1,String name2,String name3,String name4) {
		this.mapId=mapId;
		this.name1=name1;
		this.name2=name2;
		this.name3=name3;
		this.name4=name4;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMapId() {
		return mapId;
	}

	public void setMapId(int mapId) {
		this.mapId = mapId;
	}

	public String getName1() {
		return name1;
	}

	public void setName1(String name) {
		this.name1 = name;
	}

	public String getName2() {
		return name2;
	}

	public void setName2(String name) {
		this.name2 = name;
	}

	public String getName3() {
		return name3;
	}

	public void setName3(String name3) {
		this.name3 = name3;
	}

	public String getName4() {
		return name4;
	}

	public void setName4(String name4) {
		this.name4 = name4;
	}

	public List<PolygonTO> getPolygons() {
		return polygons;
	}

	public void setPolygons(List<PolygonTO> polygons) {
		this.polygons = polygons;
	}
	
	public void addPolygon(PolygonTO polygon) {
		if(polygons==null) {
			polygons=new ArrayList<PolygonTO>();
		}
		polygons.add(polygon);
	}
	
	public boolean isInsideArea(Polygon areaPolygon) {
		boolean inside=false;
		for(PolygonTO p : polygons) {
			if(p.getGeometryPolygon().intersects(areaPolygon)) {
				inside=true;
			}
		}
		return inside;
	}
	
	public boolean isNew() {
		if(id==-1) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public String toString() {
		return name1+" "+name2;
	}
	
}

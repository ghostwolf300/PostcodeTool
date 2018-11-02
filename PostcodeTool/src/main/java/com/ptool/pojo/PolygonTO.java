package com.ptool.pojo;

import java.util.ArrayList;
import java.util.List;

import org.locationtech.jts.geom.Polygon;

public class PolygonTO {
	
	private int id;
	private List<Ring> rings=null;
	
	public PolygonTO() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Ring> getRings() {
		return rings;
	}

	public void setRings(List<Ring> rings) {
		this.rings = rings;
	}
	
	public void addRing(Ring ring) {
		if(rings==null) {
			rings=new ArrayList<Ring>();
		}
		rings.add(ring);
	}
	
	public Polygon getGeometryPolygon(){
		Polygon geoPoly=null;
		return geoPoly;
	}
	
	
}

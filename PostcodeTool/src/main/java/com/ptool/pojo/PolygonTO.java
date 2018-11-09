package com.ptool.pojo;

import java.util.ArrayList;
import java.util.List;

import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.Polygon;

public class PolygonTO {
	
	private int id;
	private List<RingTO> rings=null;
	
	public PolygonTO() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<RingTO> getRings() {
		return rings;
	}

	public void setRings(List<RingTO> rings) {
		this.rings = rings;
	}
	
	public void addRing(RingTO ring) {
		if(rings==null) {
			rings=new ArrayList<RingTO>();
		}
		rings.add(ring);
	}
	
	public Polygon getGeometryPolygon(){
		Polygon geoPoly=null;
		GeometryFactory factory=null;
		if(rings!=null && rings.size()>0) {
			factory=new GeometryFactory();
			RingTO shell=rings.get(0);
			if(rings.size()>1) {
				LinearRing[] holes=getHoles();
				geoPoly=factory.createPolygon(shell.getLinearRing(),holes);
			}
			else {
				geoPoly=factory.createPolygon(shell.getLinearRing());
			}
		}
		return geoPoly;
	}
	
	private LinearRing[] getHoles() {
		LinearRing[] holes=new LinearRing[rings.size()-1];
		int h=0;
		for(int i=1;i<rings.size();i++) {
			holes[h]=rings.get(i).getLinearRing();
			h++;
		}
		return holes;
	}

	@Override
	public String toString() {
		return Integer.toString(id);
	}
	
	
}

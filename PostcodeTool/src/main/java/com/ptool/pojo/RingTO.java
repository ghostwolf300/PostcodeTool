package com.ptool.pojo;

import java.util.ArrayList;
import java.util.List;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LinearRing;

public class RingTO {
	
	public static final int TYPE_OUTER=1;
	public static final int TYPE_INNER=2;
	
	private int id;
	private int ringType;
	private List<CoordinateTO> coordinates=null;
	
	public RingTO() {
		
	}
	
	public RingTO(int ringType) {
		this.ringType=ringType;
	}
	
	public RingTO(int ringType,List<CoordinateTO> coordinates) {
		this.ringType=ringType;
		this.coordinates=coordinates;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRingType() {
		return ringType;
	}

	public void setRingType(int ringType) {
		this.ringType = ringType;
	}

	public List<CoordinateTO> getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(List<CoordinateTO> coordinates) {
		this.coordinates = coordinates;
	}
	
	public void addCoordinates(CoordinateTO c) {
		if(coordinates==null) {
			coordinates=new ArrayList<CoordinateTO>();
		}
		coordinates.add(c);
	}
	
	public LinearRing getLinearRing() {
		GeometryFactory factory=new GeometryFactory();
		LinearRing lring=null;
		Coordinate[] cArr=new Coordinate[coordinates.size()];
		int i=0;
		for(CoordinateTO to : coordinates) {
			cArr[i]=to.getGeometryCoordinates();
			i++;
		}
		lring=factory.createLinearRing(cArr);
		return lring;
	}
	
}

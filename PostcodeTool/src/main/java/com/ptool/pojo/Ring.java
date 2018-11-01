package com.ptool.pojo;

import java.util.ArrayList;
import java.util.List;

public class Ring {
	
	public static final int TYPE_OUTER=1;
	public static final int TYPE_INNER=2;
	
	private int id;
	private int ringType;
	private List<Coordinates> coordinates=null;
	
	public Ring() {
		
	}
	
	public Ring(int ringType) {
		this.ringType=ringType;
	}
	
	public Ring(int ringType,List<Coordinates> coordinates) {
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

	public List<Coordinates> getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(List<Coordinates> coordinates) {
		this.coordinates = coordinates;
	}
	
	public void addCoordinates(Coordinates c) {
		if(coordinates==null) {
			coordinates=new ArrayList<Coordinates>();
		}
		coordinates.add(c);
	}
	
}

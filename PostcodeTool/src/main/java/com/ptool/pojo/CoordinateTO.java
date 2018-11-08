package com.ptool.pojo;

import org.locationtech.jts.geom.Coordinate;

public class CoordinateTO {
	
	private int ringId;
	private int mapId;
	private int orderNum;
	private double x;
	private double y;
	
	public CoordinateTO() {
		
	}

	public CoordinateTO(int ringId, int orderNum, double x, double y) {
		this.ringId = ringId;
		this.orderNum = orderNum;
		this.x = x;
		this.y = y;
	}

	public int getRingId() {
		return ringId;
	}

	public void setRingId(int ringId) {
		this.ringId = ringId;
	}

	public int getMapId() {
		return mapId;
	}

	public void setMapId(int mapId) {
		this.mapId = mapId;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	public Coordinate getGeometryCoordinates() {
		Coordinate coord=new Coordinate(this.x,this.y);
		return coord;
	}
	
	
}

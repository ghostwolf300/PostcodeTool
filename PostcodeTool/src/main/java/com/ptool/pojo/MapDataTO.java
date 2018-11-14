package com.ptool.pojo;

public class MapDataTO {
	
	private int id=-1;
	private String name=null;
	private double minX;
	private double minY;
	private double maxX;
	private double maxY;
	private String crs;
	
	public MapDataTO() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getMinX() {
		return minX;
	}

	public void setMinX(double minX) {
		this.minX = minX;
	}

	public double getMinY() {
		return minY;
	}

	public void setMinY(double minY) {
		this.minY = minY;
	}

	public double getMaxX() {
		return maxX;
	}

	public void setMaxX(double maxX) {
		this.maxX = maxX;
	}

	public double getMaxY() {
		return maxY;
	}

	public void setMaxY(double maxY) {
		this.maxY = maxY;
	}

	public String getCrs() {
		return crs;
	}

	public void setCrs(String crs) {
		this.crs = crs;
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
		return name+" "+crs;
	}
}

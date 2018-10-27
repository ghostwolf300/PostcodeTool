package com.ptool.geo;

public class Position {
	
	private long index;
	private double x;
	private double y;
	private double z;
	
	public Position() {
		
	}
	
	public Position(long index) {
		this.index=index;
	}
	
	public Position(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Position(double x, double y, double z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public long getIndex() {
		return index;
	}

	public void setIndex(long index) {
		this.index = index;
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

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	@Override
	public String toString() {
		return "Position [x=" + x + ", y=" + y + "]";
	}
	
}

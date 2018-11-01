package com.ptool.pojo;

public class Coordinates {
	
	private int ringId;
	private int orderNum;
	private double x;
	private double y;
	
	public Coordinates() {
		
	}

	public Coordinates(int ringId, int orderNum, double x, double y) {
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
	
	
}

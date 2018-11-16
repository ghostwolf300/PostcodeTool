package com.ptool.pojo;

public class MapImageParametersTO {
	
	public static final String FORMAT="image/png; mode=8bit";
	public static final String LAYERS="pno";
	
	public static final String CRS_EPSG_3067="EPSG:3067";
	
	//bbox
	private double minX;
	private double minY;
	private double maxX;
	private double maxY;
	
	//image size (screen size)
	private int width;
	private int height;
	
	//Coordinate system
	private String crs=null;
	
	public MapImageParametersTO() {
		crs=CRS_EPSG_3067;
	}
	
	public MapImageParametersTO(double minX,double minY,double maxX,double maxY,int width,int height,String crs) {
		this.minX=minX;
		this.minY=minY;
		this.maxX=maxX;
		this.maxY=maxY;
		this.width=width;
		this.height=height;
		this.crs=crs;
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

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getCrs() {
		return crs;
	}

	public void setCrs(String crs) {
		this.crs = crs;
	}

	@Override
	public String toString() {
		return "request=GetMap&Format="+FORMAT+"&width="+width+"&height="+height+"&layers="+LAYERS+"&bbox="+minX+","+minY+","+maxX+","+maxY+"&crs="+crs;
	}
	
	
}

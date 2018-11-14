package com.ptool.pojo;

import java.awt.Color;

public class CollectionStyleTO {
	
	private Color backgroundColor=Color.RED;
	private Color lineColor=Color.BLACK;
	private double transparency=0.25;
	private double lineThickness=1.0;
	
	public CollectionStyleTO() {
		
	}

	public CollectionStyleTO(Color backgroundColor, Color lineColor, double transparency, double lineThickness) {
		this.backgroundColor = backgroundColor;
		this.lineColor = lineColor;
		this.transparency = transparency;
		this.lineThickness = lineThickness;
	}
	
	public CollectionStyleTO(CollectionStyleTO copyStyle) {
		this.backgroundColor=copyStyle.backgroundColor;
		this.lineColor=copyStyle.lineColor;
		this.transparency=copyStyle.transparency;
		this.lineThickness=copyStyle.lineThickness;
	}
	
	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public Color getLineColor() {
		return lineColor;
	}

	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}

	public double getTransparency() {
		return transparency;
	}

	public void setTransparency(double transparency) {
		this.transparency = transparency;
	}

	public double getLineThickness() {
		return lineThickness;
	}

	public void setLineThickness(double lineThickness) {
		this.lineThickness = lineThickness;
	}
	
	public final static String toHexString(Color colour) throws NullPointerException {
		String hexColour = Integer.toHexString(colour.getRGB() & 0xffffff);
		if (hexColour.length() < 6) {
			hexColour = "000000".substring(0, 6 - hexColour.length()) + hexColour;
		}
		return "#" + hexColour;
	}
	
}

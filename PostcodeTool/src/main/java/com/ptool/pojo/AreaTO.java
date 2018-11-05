package com.ptool.pojo;

import java.util.ArrayList;
import java.util.List;

public class AreaTO {
	
	private int id=-1;
	private String name=null;
	private String backgroundColor=null;
	private String lineColor=null;
	private double lineThickness=1.0;
	private double transparency=0.25;
	private List<PostcodeTO> postcodes=null;
	
	public AreaTO() {
		
	}
	
	public AreaTO(String name) {
		this.name=name;
	}
	
	public AreaTO(String name, List<PostcodeTO> postcodes) {
		this.name=name;
		this.postcodes=postcodes;
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

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public String getLineColor() {
		return lineColor;
	}

	public void setLineColor(String lineColor) {
		this.lineColor = lineColor;
	}

	public double getLineThickness() {
		return lineThickness;
	}

	public void setLineThickness(double lineThickness) {
		this.lineThickness = lineThickness;
	}

	public double getTransparency() {
		return transparency;
	}

	public void setTransparency(double transparency) {
		this.transparency = transparency;
	}

	public List<PostcodeTO> getPostcodes() {
		return postcodes;
	}

	public void setPostcodes(List<PostcodeTO> postcodes) {
		this.postcodes = postcodes;
	}
	
	public void addPostcode(PostcodeTO postcode) {
		if(postcodes==null) {
			postcodes=new ArrayList<PostcodeTO>();
		}
		postcodes.add(postcode);
	}
	
	public boolean isNew() {
		if(id==-1) {
			return true;
		}
		else {
			return false;
		}
	}
	
}

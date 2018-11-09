package com.ptool.pojo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AreaTO {
	
	private int id=-1;
	private String name="<New>";
	private String backgroundColor="#FC33FF";
	private String lineColor="#FF0000";
	private double lineThickness=1.0;
	private double transparency=0.25;
	private Set<PostcodeTO> postcodes=null;
	
	public AreaTO() {
		
	}
	
	public AreaTO(String name) {
		this.name=name;
	}
	
	public AreaTO(String name, Set<PostcodeTO> postcodes) {
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

	public Set<PostcodeTO> getPostcodes() {
		return postcodes;
	}

	public void setPostcodes(Set<PostcodeTO> postcodes) {
		this.postcodes = postcodes;
	}
	
	public void addPostcode(PostcodeTO postcode) {
		if(postcodes==null) {
			postcodes=new HashSet<PostcodeTO>();
		}
		postcodes.add(postcode);
	}
	
	public void addPostcodes(Set<PostcodeTO> postcodes) {
		if(this.postcodes==null) {
			this.postcodes=new HashSet<PostcodeTO>();
		}
		this.postcodes.addAll(postcodes);
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

package com.ptool.pojo;

import java.util.ArrayList;
import java.util.List;

public class Postcode {
	
	private String postcode=null;
	private String name=null;
	private List<Polygon> polygons=null;
	
	public Postcode() {
		
	}
	
	public Postcode(String postcode,String name) {
		this.postcode=postcode;
		this.name=name;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Polygon> getPolygons() {
		return polygons;
	}

	public void setPolygons(List<Polygon> polygons) {
		this.polygons = polygons;
	}
	
	public void addPolygon(Polygon polygon) {
		if(polygons==null) {
			polygons=new ArrayList<Polygon>();
		}
		polygons.add(polygon);
	}

	@Override
	public String toString() {
		return "Postcode [postcode=" + postcode + ", name=" + name + "]";
	}
	
}

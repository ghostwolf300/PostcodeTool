package com.ptool.pojo;

import java.util.ArrayList;
import java.util.List;

import org.locationtech.jts.geom.Polygon;

public class PostcodeTO {
	
	private String postcode=null;
	private String name=null;
	private List<PolygonTO> polygons=null;
	
	public PostcodeTO() {
		
	}
	
	public PostcodeTO(String postcode,String name) {
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

	public List<PolygonTO> getPolygons() {
		return polygons;
	}

	public void setPolygons(List<PolygonTO> polygons) {
		this.polygons = polygons;
	}
	
	public void addPolygon(PolygonTO polygon) {
		if(polygons==null) {
			polygons=new ArrayList<PolygonTO>();
		}
		polygons.add(polygon);
	}
	
	public boolean isInsideArea(Polygon areaPolygon) {
		boolean inside=false;
		for(PolygonTO p : polygons) {
			if(p.getGeometryPolygon().intersects(areaPolygon)) {
				inside=true;
			}
		}
		return inside;
	}

	@Override
	public String toString() {
		return "Postcode [postcode=" + postcode + ", name=" + name + "]";
	}
	
}

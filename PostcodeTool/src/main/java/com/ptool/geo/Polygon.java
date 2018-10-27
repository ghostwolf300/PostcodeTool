package com.ptool.geo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Polygon extends GeometryObject implements IGeometryObject {
	
	private List<Arc> arcs=null;

	public Polygon() {
		super(GeometryObject.Type.Polygon);
	}
	
	public List<Arc> getArcs() {
		return arcs;
	}

	public void setArcs(List<Arc> arcs) {
		this.arcs = arcs;
	}
	
	public void addArc(Arc arc) {
		if(arcs==null) {
			arcs=new ArrayList<Arc>();
		}
		arcs.add(arc);
	}
	
	public Arc getArc(int i) {
		Arc arc=null;
		if(arcs!=null) {
			arc=arcs.get(i);
		}
		return arc;
	}
	
	public static Polygon createPolygon(JSONObject jsonObject) {
		Polygon polygon=null;
		if(jsonObject.get("type").equals(GeometryObject.POLYGON)) {
			polygon=new Polygon();
			JSONObject properties=(JSONObject) jsonObject.get("properties");
			
			if(properties!=null) {
				Set<String> keys=properties.keySet();
				for(String key : keys) {
					polygon.addProperty(key,properties.get(key));
				}
			}
			
			JSONArray jsonArcs=(JSONArray) jsonObject.get("arcs");
			
			for(Object o : jsonArcs) {
				JSONArray jsonArcArr=(JSONArray)o;
				for(Object oo : jsonArcArr) {
					long index=(Long) oo;
					polygon.addArc(new Arc(index));
				}
			}
			
		}
		return polygon;
	}
	
	
}

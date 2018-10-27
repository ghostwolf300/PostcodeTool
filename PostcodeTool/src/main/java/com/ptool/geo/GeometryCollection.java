package com.ptool.geo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class GeometryCollection extends GeometryObject implements IGeometryObject {
	
	public List<GeometryObject> geometries=null;
	
	public GeometryCollection() {
		super(GeometryObject.Type.GeometryCollection);
	}
	
	public GeometryCollection(Map<String, Object> properties) {
		super(GeometryObject.Type.GeometryCollection,properties);
	}

	public List<GeometryObject> getGeometries() {
		return geometries;
	}

	public void setGeometries(List<GeometryObject> geometries) {
		this.geometries = geometries;
	}
	
	public void addGeometry(GeometryObject geo) {
		if(geometries==null) {
			geometries=new ArrayList<GeometryObject>();
		}
		geometries.add(geo);
	}
	
	public static GeometryCollection createGeometryCollection(JSONObject jsonObject) {
		GeometryCollection geoCollection=null;
		if(jsonObject.get("type").equals(GeometryObject.GEOMETRY_COLLECTION)) {
			geoCollection=new GeometryCollection();
			
			JSONObject properties=(JSONObject) jsonObject.get("properties");
			if(properties!=null) {
				Set<String> keys=properties.keySet();
				for(String key : keys) {
					geoCollection.addProperty(key,properties.get(key));
				}
			}
			
			JSONArray geometries=(JSONArray) jsonObject.get("geometries");
			for(Object o : geometries) {
				JSONObject geoJson=(JSONObject)o;
				String type=(String)geoJson.get("type");
				if(type.equals(GeometryObject.GEOMETRY_COLLECTION)) {
					GeometryCollection gc=GeometryCollection.createGeometryCollection(geoJson);
					geoCollection.addGeometry(gc);
				}
				else if(type.equals(GeometryObject.POLYGON)) {
					Polygon poly=Polygon.createPolygon(geoJson);
					geoCollection.addGeometry(poly);
				}
				else {
					System.out.println("Unknown geometry type: "+type);
				}
				
			}
			
		}
		return geoCollection;
	}
	
}

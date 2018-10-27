package com.ptool.geo;

import java.util.HashMap;
import java.util.Map;

public abstract class GeometryObject {
	
	public static final String GEOMETRY_COLLECTION="GeometryCollection";
	public static final String POLYGON="Polygon";
	
	public static enum Type {LineString,MultiLineString,Polygon,MultiPolygon,GeometryCollection};
	
	private Type type;
	private Map<String,Object> properties=null;
	
	public GeometryObject(Type type) {
		this.type=type;
	}
	
	public GeometryObject(Type type,Map<String, Object> properties) {
		this.type=type;
		this.properties = properties;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}
	
	public void addProperty(String name,Object value) {
		if(properties==null) {
			properties=new HashMap<String,Object>();
		}
		properties.put(name, value);
	}
	
	public Object getProperty(String key) {
		Object value=null;
		if(properties.containsKey(key)) {
			value=properties.get(key);
		}
		return value;
	}
	
	
	
}

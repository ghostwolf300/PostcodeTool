package com.ptool.json;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.ptool.pojo.Coordinates;
import com.ptool.pojo.Polygon;
import com.ptool.pojo.Postcode;
import com.ptool.pojo.Ring;

public class JSONUtil {
	
	private static JSONUtil instance;
	
	private JSONUtil() {
		
	}
	
	public static synchronized JSONUtil getInstance() {
		if(instance==null) {
			instance=new JSONUtil();
		}
		return instance;
	}
	
	public List<Postcode> convert(JSONObject json){
		List<Postcode> postcodes=new ArrayList<Postcode>();
		JSONArray postcodeArray=(JSONArray) json.get("features");
		
		for(Object obj : postcodeArray) {
			JSONObject pc=(JSONObject) obj;
			postcodes.add(toPostcode(pc));
		}
		
		return postcodes;
	}
	
	public void writeToFile(JSONObject json,String path) {
		
		FileWriter file=null;
		
		try {
			file = new FileWriter(path);
			file.write(json.toJSONString());
			file.flush();
			System.out.println("Successfully Copied JSON Object to File...");
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if(file!=null) {
				try {
					file.close();
				} 
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public JSONObject readFromFile(String path) {
		JSONObject json=null;
		JSONParser parser=new JSONParser();
		
		try {
			Object obj=parser.parse(new FileReader(path));
			json=(JSONObject) obj;
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return json;
	}
	
	private Postcode toPostcode(JSONObject json) {
		JSONObject prop=(JSONObject) json.get("properties");
		Postcode postcode=new Postcode();
		postcode.setPostcode((String)prop.get("posti_alue"));
		postcode.setName((String)prop.get("nimi"));
		JSONObject jsonGeom=(JSONObject) json.get("geometry");
		JSONArray jsonCoordinates=(JSONArray) jsonGeom.get("coordinates");
		
		for(Object o : jsonCoordinates) {
			JSONArray jsonPolygon=(JSONArray) o;
			postcode.addPolygon(toPolygon(jsonPolygon));
		}
		
		return postcode;
	}
	
	private Polygon toPolygon(JSONArray jsonPolygon) {
		Polygon polygon=new Polygon();
		JSONArray jsonOuterRing=(JSONArray)jsonPolygon.get(0);
		Ring outerRing=toRing(jsonOuterRing);
		outerRing.setRingType(Ring.TYPE_OUTER);
		polygon.addRing(outerRing);
		
		Ring innerRing=null;
		for(int i=1;i<jsonPolygon.size();i++) {
			JSONArray jsonRing=(JSONArray)jsonPolygon.get(i);
			innerRing=toRing(jsonRing);
			innerRing.setRingType(Ring.TYPE_INNER);
			polygon.addRing(innerRing);
		}
		return polygon;
	}
	
	private Ring toRing(JSONArray jsonRing) {
		Ring ring=new Ring();
		JSONArray jsonCoordinates=null;
		Coordinates coordinates=null;
		for(int i=0;i<jsonRing.size();i++) {
			jsonCoordinates=(JSONArray)jsonRing.get(i);
			coordinates=new Coordinates();
			coordinates.setOrderNum(i);
			
			//System.out.println(jsonCoordinates.get(0)+"\t"+jsonCoordinates.get(1));
			
			Object objX=jsonCoordinates.get(0);
			if(objX instanceof Double) {
				coordinates.setX((Double)objX);
			}
			else if(objX instanceof Long) {
				coordinates.setX(((Long)objX).doubleValue());
			}
			else {
				//unknown
			}
 			
			Object objY=jsonCoordinates.get(1);
			if(objY instanceof Double) {
				coordinates.setY((Double)objY);
			}
			else if(objY instanceof Long) {
				coordinates.setY(((Long)objY).doubleValue());
			}
			else {
				//unknown
			}
			ring.addCoordinates(coordinates);
			
		}
		return ring;
	}
	
}

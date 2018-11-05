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

import com.ptool.pojo.CoordinateTO;
import com.ptool.pojo.PolygonTO;
import com.ptool.pojo.PostcodeTO;
import com.ptool.pojo.RingTO;

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
	
	public List<PostcodeTO> convert(JSONObject json){
		List<PostcodeTO> postcodes=new ArrayList<PostcodeTO>();
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
	
	private PostcodeTO toPostcode(JSONObject json) {
		JSONObject prop=(JSONObject) json.get("properties");
		PostcodeTO postcode=new PostcodeTO();
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
	
	private PolygonTO toPolygon(JSONArray jsonPolygon) {
		PolygonTO polygon=new PolygonTO();
		JSONArray jsonOuterRing=(JSONArray)jsonPolygon.get(0);
		RingTO outerRing=toRing(jsonOuterRing);
		outerRing.setRingType(RingTO.TYPE_OUTER);
		polygon.addRing(outerRing);
		
		RingTO innerRing=null;
		for(int i=1;i<jsonPolygon.size();i++) {
			JSONArray jsonRing=(JSONArray)jsonPolygon.get(i);
			innerRing=toRing(jsonRing);
			innerRing.setRingType(RingTO.TYPE_INNER);
			polygon.addRing(innerRing);
		}
		return polygon;
	}
	
	private RingTO toRing(JSONArray jsonRing) {
		RingTO ring=new RingTO();
		JSONArray jsonCoordinates=null;
		CoordinateTO coordinates=null;
		for(int i=0;i<jsonRing.size();i++) {
			jsonCoordinates=(JSONArray)jsonRing.get(i);
			coordinates=new CoordinateTO();
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

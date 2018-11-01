package com.ptool.json;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.ptool.pojo.Postcode;

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
		List<Postcode> postcodes=null;
		JSONArray postcodeArray=(JSONArray) json.get("features");
		for(Object obj : postcodeArray) {
			JSONObject pc=(JSONObject) obj;
			JSONObject prop=(JSONObject) pc.get("properties");
			System.out.println(prop.get("posti_alue")+"\t"+prop.get("nimi"));
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
	
}

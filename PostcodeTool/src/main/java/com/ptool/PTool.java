package com.ptool;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.ptool.csv.MyCSVReader;
import com.ptool.db.DAOFactory;
import com.ptool.db.IPostcodeDAO;
import com.ptool.geo.Arc;
import com.ptool.geo.GeometryCollection;
import com.ptool.geo.GeometryObject;
import com.ptool.geo.Polygon;
import com.ptool.geo.Position;
import com.ptool.geo.Topology;
import com.ptool.json.JSONUtil;
import com.ptool.kml.KMLUtil;
import com.ptool.kml.KMLWriter;
import com.ptool.net.NetUtil;
import com.ptool.pojo.Postcode;

public class PTool {
	
	private JSONArray jsonArcs=null;
	
	public PTool() {
		
	}
	
	public static void main(String[] args) {
		
		PTool tool=new PTool();
		//tool.fetchPostcodeAreas();
		tool.test();
		//tool.readTopoJson();
		//tool.convertCSVToKML();
		//tool.testDerby();
		

	}
	
	public void convertCSVToKML() {
		List<String[]> postcodes=MyCSVReader.getInstance().readPostcodeFile("postinumerot 20150102.csv");
		KMLUtil kmlUtil=KMLUtil.getInstance();
		kmlUtil.createKMLDocument("Postinumerot");
		
		int i=0;
		int fileNumber=1;
		for(String[] pc : postcodes) {
			if(i==1000) {
				KMLWriter.getInstance().write(kmlUtil.getKmlDoc(), "from_csv_"+fileNumber+".kml");
				kmlUtil.createKMLDocument("Postinumerot");
				fileNumber++;
				i=0;
			}
			if(!pc[2].isEmpty()) {
				kmlUtil.addPostcode(pc[1], pc[2]);
			}
			i++;
		}
		KMLWriter.getInstance().write(kmlUtil.getKmlDoc(), "from_csv_"+fileNumber+".kml");
	}
	
	
	/*public void readTopoJson() {
		
		JSONParser parser=new JSONParser();
		JSONObject jsonObj=null;
		
		try {
			jsonObj=(JSONObject)parser.parse(new FileReader("postinumeroalueet 2018.tjson"));
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
		
		System.out.println(jsonObj.get("type"));
		
		JSONObject objects=((JSONObject)jsonObj.get("objects"));
		jsonArcs=(JSONArray) jsonObj.get("arcs");
		
		JSONObject posti=(JSONObject) objects.get("posti2018");
		JSONArray areas=(JSONArray) posti.get("geometries");
		
		List<GeometryObject> geoObjList=new ArrayList<GeometryObject>();
		
		for(Object o : areas) {
			JSONObject json=(JSONObject)o;
			String type=(String)json.get("type");
			//JSONObject prop=(JSONObject) json.get("properties");
			//String areaCode=(String) prop.get("posti_alue");
			if(areaCode.equals("90480") || areaCode.equals("32250") || areaCode.equals("22840")) {
				if(type.equals("GeometryCollection")) {
					geoObjList.add(GeometryCollection.createGeometryCollection(json));
				}
				else if(type.equals("Polygon")) {
					geoObjList.add(Polygon.createPolygon(json));
				}
			}
			if(type.equals("GeometryCollection")) {
				geoObjList.add(GeometryCollection.createGeometryCollection(json));
			}
			else if(type.equals("Polygon")) {
				geoObjList.add(Polygon.createPolygon(json));
			}
		}
		
		System.out.println(geoObjList.size());
		
		for(GeometryObject geo : geoObjList) {
			
			String text="alue : "+geo.getProperty("posti_alue")+"\t";
			
			if(geo.getProperty("posti_alue").equals("32250")) {
				System.out.println("virheellinen");
			}
			
			if(geo.getType()==GeometryObject.Type.GeometryCollection) {
				text+="tyyppi: GeometryCollection";
				GeometryCollection geoColl=(GeometryCollection) geo;
				for(GeometryObject o : geoColl.geometries) {
					Polygon poly=(Polygon)o;
					for(Arc arc : poly.getArcs()) {
						addCoordinates(arc);
					}
				}
			}
			else if(geo.getType()==GeometryObject.Type.Polygon) {
				text+="tyyppi: Polygon";
				Polygon poly=(Polygon) geo;
				for(Arc arc : poly.getArcs()) {
					addCoordinates(arc);
				}
			}
			else {
				System.out.println("Unknown Geometry");
			}
			
			System.out.println(text);
		}
		
		Document doc=KMLUtil.getInstance().buildKML(geoObjList);
		KMLWriter.getInstance().write(doc, "testi.kml");
	}*/
	
	public void addCoordinates(Arc arc) {
		int index=(int)arc.getIndex();
		JSONArray jsonPositions=null;
		JSONArray jsonPos=null;
		double x;
		double y;
		
		if(index>=0) {
			jsonPositions=(JSONArray) jsonArcs.get(index);
			for(Object o : jsonPositions) {
				jsonPos=(JSONArray) o;
				//System.out.println(arc.getIndex()+"\tX: "+jsonPos.get(0)+"\tY: "+jsonPos.get(1));
				x=(Double) jsonPos.get(0);
				y=(Double) jsonPos.get(1);
				Position p=new Position(x,y);
				arc.addPosition(p);
			}
		}
		else {
			index=~index;
			jsonPositions=(JSONArray) jsonArcs.get(index);
			int lastIndex=jsonPositions.size()-1;
			for(int i=lastIndex;i>=0;i--) {
				jsonPos=(JSONArray)jsonPositions.get(i);
				//System.out.println(arc.getIndex()+"\tX: "+jsonPos.get(0)+"\tY: "+jsonPos.get(1));
				x=(Double) jsonPos.get(0);
				y=(Double) jsonPos.get(1);
				Position p=new Position(x,y);
				arc.addPosition(p);
			}
		}
	}
	
	public void testDerby() {
		IPostcodeDAO dao=DAOFactory.getDAOFactory(DAOFactory.DERBY).getPostcodeDAO();
		dao.createDB();
	}
	
	public void fetchPostcodeAreas() {
		//JSONObject json=NetUtil.getInstance().getPostcodeJSON();
		
		//JSONUtil.getInstance().writeToFile(json, "pno_net_test_new.json");
		
		JSONObject json=JSONUtil.getInstance().readFromFile("pno_net_test_new.json");
		List<Postcode> postcodes=JSONUtil.getInstance().convert(json);
		
		IPostcodeDAO dao=DAOFactory.getDAOFactory(DAOFactory.DERBY).getPostcodeDAO();
		
		for(Postcode pc : postcodes) {
			System.out.println(pc.toString());
		}
		dao.clearTables();
		dao.savePostcodes(postcodes);
		
	}
	
	public void test() {
		IPostcodeDAO dao=DAOFactory.getDAOFactory(DAOFactory.DERBY).getPostcodeDAO();
		//Postcode p=dao.getPostcode("41325");
		//System.out.println(p.getPostcode()+"\t"+p.getName());
		List<Postcode> postcodes=dao.getAllPostcodes();
		KMLUtil util=KMLUtil.getInstance();
		util.createKMLDocument("my test");
		for(Postcode pc : postcodes) {
			util.addPostcode(pc);
		}
		KMLWriter.getInstance().write(util.getKmlDoc(),"from_my_db_2.kml");
		
		
	}
	
	

}

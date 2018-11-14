package com.ptool;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jdom2.Document;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.locationtech.jts.geom.Polygon;

import com.ptool.controller.DefaultController;
import com.ptool.csv.MyCSVReader;
import com.ptool.db.DAOFactory;
import com.ptool.db.ICollectionDAO;
import com.ptool.db.IMapAreaDAO;
import com.ptool.geo.Arc;
import com.ptool.geo.Position;
import com.ptool.gui.MTFrame;
import com.ptool.json.JSONUtil;
import com.ptool.kml.KMLReader;
import com.ptool.kml.KMLUtil;
import com.ptool.kml.KMLWriter;
import com.ptool.net.NetUtil;
import com.ptool.pojo.CollectionStyleTO;
import com.ptool.pojo.CollectionTO;
import com.ptool.pojo.MapAreaTO;

public class MapTool {
	
	public MapTool() {
		
	}
	
	public static void main(String[] args) {
		
		//MapTool tool=new MapTool();
		//List<MapAreaTO> postcodes=tool.getPostcodesWFS();
		//List<PostcodeTO> postcodes=tool.getPostcodesFromFile("pno_net_test_new.json");
		//tool.saveMapAreas(postcodes);
		//tool.createPostcodeKML();
		//tool.findPostcodesInsideArea();
		//tool.createPostcodeMap(2);
		DefaultController controller=new DefaultController();
		MTFrame gui=new MTFrame(controller);
		/*controller.loadMap();
		controller.loadPostcodes();
		controller.loadAreas();*/
		gui.setVisible(true);
		

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
	
	public void testDerby() {
		IMapAreaDAO dao=DAOFactory.getDAOFactory(DAOFactory.DERBY1).getMapAreaDAO();
		dao.createDB();
	}
	
	public List<MapAreaTO> getPostcodesWFS(){
		//Retrieves postcodes from http://geo.stat.fi/geoserver/postialue/wfs
		//Tilastokeskuksen palvelurajapinta (WFS)
		//Huom! Hakee 2018 tiedot. Muuta NetUtil luokkaa, jos haluat hakea tiedot muilta vuosilta
		JSONObject json=NetUtil.getInstance().getPostcodeJSON();
		List<MapAreaTO> postcodes=JSONUtil.getInstance().convert(json);
		return postcodes;
	}
	
	public List<MapAreaTO> getPostcodesFromFile(String path){
		JSONObject json=JSONUtil.getInstance().readFromFile(path);
		List<MapAreaTO> postcodes=JSONUtil.getInstance().convert(json);
		return postcodes;
	}
	
	public void saveMapAreas(List<MapAreaTO> postcodes) {
		IMapAreaDAO dao=DAOFactory.getDAOFactory(DAOFactory.DERBY2).getMapAreaDAO();
		//dao.clearTables();
		
		//1  EPSG 4326 (KML friendly)
		//2 default CRS EPSG 3067
		for(MapAreaTO pc : postcodes) {
			pc.setMapId(2);
		}
		
		dao.saveMapAreas(postcodes);
	}
	
	public void createPostcodeMap() {
		IMapAreaDAO dao=DAOFactory.getDAOFactory(DAOFactory.DERBY1).getMapAreaDAO();
		List<MapAreaTO> postcodes=dao.findAllMapAreas(1);
		KMLUtil util=KMLUtil.getInstance();
		util.createKMLDocument("Postialueet 2018");
		for(MapAreaTO pc : postcodes) {
			util.addPostcode(pc);
		}
		KMLWriter.getInstance().write(util.getKmlDoc(),"postialueet_2018.kml");
	}
	
	public void createPostcodeMap(int areaId) {
		IMapAreaDAO pcDao=DAOFactory.getDAOFactory(DAOFactory.DERBY1).getMapAreaDAO();
		ICollectionDAO areaDao=DAOFactory.getDAOFactory(DAOFactory.DERBY1).getCollectionDAO();
		CollectionTO area=areaDao.findCollectionById(areaId);
		List<MapAreaTO> postcodes=pcDao.findMapAreasByCollectionId(area.getId());
		KMLUtil util=KMLUtil.getInstance();
		util.createKMLDocument(area.getName());
		for(MapAreaTO pc : postcodes) {
			util.addPostcode(pc);
		}
		KMLWriter.getInstance().write(util.getKmlDoc(),area.getName()+".kml");
		
	}
	
	public void findPostcodesInsideArea() {
		//luetaan sis��n alue kml ja etsit��n sis��n j��v�t postialueet
		String areaKMLFile="kml/Rajattu alue.kml";
		Document doc=KMLReader.getInstance().readKMLFile(areaKMLFile);
		KMLUtil kmlUtil=KMLUtil.getInstance();
		kmlUtil.setKmlDoc(doc);
		List<Polygon> polygons=kmlUtil.extractPolygons();
		
		IMapAreaDAO pcDao=DAOFactory.getDAOFactory(DAOFactory.DERBY1).getMapAreaDAO();
		List<MapAreaTO> postcodes=pcDao.findAllMapAreas(1);
		Set<MapAreaTO> insidePostcodes=new HashSet<MapAreaTO>();
		
		for(Polygon p : polygons) {
			for(MapAreaTO postcode : postcodes) {
				if(postcode.isInsideArea(p)) {
					insidePostcodes.add(postcode);
				}
			}
		}
		
		for(MapAreaTO pc : insidePostcodes) {
			System.out.println(pc);
		}
		
		System.out.println("size: "+insidePostcodes.size());
		
		ICollectionDAO areaDao=DAOFactory.getDAOFactory(DAOFactory.DERBY1).getCollectionDAO();
		CollectionTO myArea=new CollectionTO("Rajaustesti 05112018");
		CollectionStyleTO style=new CollectionStyleTO();
		style.setBackgroundColor(Color.decode("#FF3642"));
		style.setTransparency(0.15);
		myArea.setStyle(style);
		myArea.setMapAreas(insidePostcodes);
		areaDao.saveCollection(myArea);
		
		
	}
	
	public void findModifiedPostcodes() {
		//luetaan sis��n muutettu (v�ritetty) postialue kml ja poimitaan muuttuneet alueet 
		String modifiedKMLFile="";
	}
	
	

}

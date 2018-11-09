package com.ptool;

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
import com.ptool.db.IAreaDAO;
import com.ptool.db.IPostcodeDAO;
import com.ptool.geo.Arc;
import com.ptool.geo.Position;
import com.ptool.gui.PToolFrame;
import com.ptool.json.JSONUtil;
import com.ptool.kml.KMLReader;
import com.ptool.kml.KMLUtil;
import com.ptool.kml.KMLWriter;
import com.ptool.net.NetUtil;
import com.ptool.pojo.AreaTO;
import com.ptool.pojo.PostcodeTO;

public class PTool {
	
	public PTool() {
		
	}
	
	public static void main(String[] args) {
		
		//PTool tool=new PTool();
		//List<PostcodeTO> postcodes=tool.getPostcodesWFS();
		//List<PostcodeTO> postcodes=tool.getPostcodesFromFile("pno_net_test_new.json");
		//tool.savePostcodes(postcodes);
		//tool.createPostcodeKML();
		//tool.findPostcodesInsideArea();
		//tool.createPostcodeMap(2);
		DefaultController controller=new DefaultController();
		PToolFrame gui=new PToolFrame(controller);
		controller.loadMap();
		controller.loadPostcodes();
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
		IPostcodeDAO dao=DAOFactory.getDAOFactory(DAOFactory.DERBY).getPostcodeDAO();
		dao.createDB();
	}
	
	public List<PostcodeTO> getPostcodesWFS(){
		//Retrieves postcodes from http://geo.stat.fi/geoserver/postialue/wfs
		//Tilastokeskuksen palvelurajapinta (WFS)
		//Huom! Hakee 2018 tiedot. Muuta NetUtil luokkaa, jos haluat hakea tiedot muilta vuosilta
		JSONObject json=NetUtil.getInstance().getPostcodeJSON();
		List<PostcodeTO> postcodes=JSONUtil.getInstance().convert(json);
		return postcodes;
	}
	
	public List<PostcodeTO> getPostcodesFromFile(String path){
		JSONObject json=JSONUtil.getInstance().readFromFile(path);
		List<PostcodeTO> postcodes=JSONUtil.getInstance().convert(json);
		return postcodes;
	}
	
	public void savePostcodes(List<PostcodeTO> postcodes) {
		IPostcodeDAO dao=DAOFactory.getDAOFactory(DAOFactory.DERBY).getPostcodeDAO();
		//dao.clearTables();
		
		//1  EPSG 4326 (KML friendly)
		//101 default CRS EPSG 3067
		for(PostcodeTO pc : postcodes) {
			pc.setMapId(1);
		}
		
		dao.savePostcodes(postcodes);
	}
	
	public void createPostcodeMap() {
		IPostcodeDAO dao=DAOFactory.getDAOFactory(DAOFactory.DERBY).getPostcodeDAO();
		List<PostcodeTO> postcodes=dao.findAllPostcodes(1);
		KMLUtil util=KMLUtil.getInstance();
		util.createKMLDocument("Postialueet 2018");
		for(PostcodeTO pc : postcodes) {
			util.addPostcode(pc);
		}
		KMLWriter.getInstance().write(util.getKmlDoc(),"postialueet_2018.kml");
	}
	
	public void createPostcodeMap(int areaId) {
		IPostcodeDAO pcDao=DAOFactory.getDAOFactory(DAOFactory.DERBY).getPostcodeDAO();
		IAreaDAO areaDao=DAOFactory.getDAOFactory(DAOFactory.DERBY).getAreaDAO();
		AreaTO area=areaDao.findAreaById(areaId);
		List<PostcodeTO> postcodes=pcDao.findPostcodesByAreaId(areaId,1);
		KMLUtil util=KMLUtil.getInstance();
		util.createKMLDocument(area.getName());
		for(PostcodeTO pc : postcodes) {
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
		
		IPostcodeDAO pcDao=DAOFactory.getDAOFactory(DAOFactory.DERBY).getPostcodeDAO();
		List<PostcodeTO> postcodes=pcDao.findAllPostcodes(1);
		Set<PostcodeTO> insidePostcodes=new HashSet<PostcodeTO>();
		
		for(Polygon p : polygons) {
			for(PostcodeTO postcode : postcodes) {
				if(postcode.isInsideArea(p)) {
					insidePostcodes.add(postcode);
				}
			}
		}
		
		for(PostcodeTO pc : insidePostcodes) {
			System.out.println(pc);
		}
		
		System.out.println("size: "+insidePostcodes.size());
		
		IAreaDAO areaDao=DAOFactory.getDAOFactory(DAOFactory.DERBY).getAreaDAO();
		AreaTO myArea=new AreaTO("Rajaustesti 05112018");
		myArea.setBackgroundColor("#FF3642");
		myArea.setTransparency(0.15);
		myArea.setPostcodes(insidePostcodes);
		areaDao.saveArea(myArea);
		
		
	}
	
	public void findModifiedPostcodes() {
		//luetaan sis��n muutettu (v�ritetty) postialue kml ja poimitaan muuttuneet alueet 
		String modifiedKMLFile="";
	}
	
	

}

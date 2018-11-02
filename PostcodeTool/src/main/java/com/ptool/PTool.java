package com.ptool;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.ptool.csv.MyCSVReader;
import com.ptool.db.DAOFactory;
import com.ptool.db.IPostcodeDAO;
import com.ptool.geo.Arc;
import com.ptool.geo.Position;
import com.ptool.json.JSONUtil;
import com.ptool.kml.KMLUtil;
import com.ptool.kml.KMLWriter;
import com.ptool.net.NetUtil;
import com.ptool.pojo.Postcode;

public class PTool {
	
	public PTool() {
		
	}
	
	public static void main(String[] args) {
		
		PTool tool=new PTool();
		List<Postcode> postcodes=tool.getPostcodesWFS();
		//List<Postcode> postcodes=tool.getPostcodesFromFile("filename.json");
		tool.savePostcodes(postcodes);
		tool.createPostcodeKML();
		

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
	
	public List<Postcode> getPostcodesWFS(){
		//Retrieves postcodes from http://geo.stat.fi/geoserver/postialue/wfs
		//Tilastokeskuksen palvelurajapinta (WFS)
		//Huom! Hakee 2018 tiedot. Muuta NetUtil luokkaa, jos haluat hakea tiedot muilta vuosilta
		JSONObject json=NetUtil.getInstance().getPostcodeJSON();
		List<Postcode> postcodes=JSONUtil.getInstance().convert(json);
		return postcodes;
	}
	
	public List<Postcode> getPostcodesFromFile(String path){
		JSONObject json=JSONUtil.getInstance().readFromFile(path);
		List<Postcode> postcodes=JSONUtil.getInstance().convert(json);
		return postcodes;
	}
	
	public void savePostcodes(List<Postcode> postcodes) {
		IPostcodeDAO dao=DAOFactory.getDAOFactory(DAOFactory.DERBY).getPostcodeDAO();
		dao.clearTables();
		dao.savePostcodes(postcodes);
	}
	
	public void createPostcodeKML() {
		IPostcodeDAO dao=DAOFactory.getDAOFactory(DAOFactory.DERBY).getPostcodeDAO();
		List<Postcode> postcodes=dao.getAllPostcodes();
		KMLUtil util=KMLUtil.getInstance();
		util.createKMLDocument("Postialueet 2018");
		for(Postcode pc : postcodes) {
			util.addPostcode(pc);
		}
		KMLWriter.getInstance().write(util.getKmlDoc(),"postialueet_2018.kml");
		
	}
	
	public void findPostcodesInsideArea() {
		//luetaan sis‰‰n alue kml ja etsit‰‰n sis‰‰n j‰‰v‰t postialueet
		String areaKMLFile="";
	}
	
	public void findModifiedPostcodes() {
		//luetaan sis‰‰n muutettu (v‰ritetty) postialue kml ja poimitaan muuttuneet alueet 
		String modifiedKMLFile="";
	}
	
	

}

package com.ptool.service;

import java.util.List;
import java.util.Set;

import com.ptool.db.DAOFactory;
import com.ptool.db.IAreaDAO;
import com.ptool.db.IMapDAO;
import com.ptool.db.IPostcodeDAO;
import com.ptool.model.AreaModel;
import com.ptool.model.MapModel;
import com.ptool.model.PostcodeModel;
import com.ptool.pojo.AreaTO;
import com.ptool.pojo.MapDataTO;
import com.ptool.pojo.PostcodeTO;

public class PToolService {
	
	private MapModel mapModel=null;
	private PostcodeModel postcodeModel=null;
	private AreaModel areaModel=null;
	
	private IMapDAO mapDAO=null;
	private IPostcodeDAO postcodeDAO=null;
	private IAreaDAO areaDAO=null;
	
	public PToolService() {
		mapModel=new MapModel();
		postcodeModel=new PostcodeModel();
		areaModel=new AreaModel();
		
		mapDAO=DAOFactory.getDAOFactory(DAOFactory.DERBY).getMapDAO();
		postcodeDAO=DAOFactory.getDAOFactory(DAOFactory.DERBY).getPostcodeDAO();
		areaDAO=DAOFactory.getDAOFactory(DAOFactory.DERBY).getAreaDAO();
	}
	
	public PostcodeModel getPostcodeModel() {
		return postcodeModel;
	}

	public void setPostcodeModel(PostcodeModel postcodeModel) {
		this.postcodeModel = postcodeModel;
	}

	public AreaModel getAreaModel() {
		return areaModel;
	}

	public void setAreaModel(AreaModel areaModel) {
		this.areaModel = areaModel;
	}
	
	public MapModel getMapModel() {
		return mapModel;
	}

	public void setMapModel(MapModel mapModel) {
		this.mapModel = mapModel;
	}

	public void loadMap(int id) {
		MapDataTO mapData=mapDAO.findMapById(id);
		mapModel.setMapData(mapData);
	}

	public void loadPostcodes(int mapId) {
		List<PostcodeTO> postcodes=postcodeDAO.findAllPostcodes(mapId);
		postcodeModel.setPostcodes(postcodes);
	}
	
	public void loadAreas() {
		List<AreaTO> areas=areaDAO.findAllAreas();
		areaModel.setAreas(areas);
	}
	
	public void selectPostcode(PostcodeTO postcode) {
		postcodeModel.setSelectedPostcode(postcode);
	}
	
	public void selectPostcodes(Set<PostcodeTO> postcodes) {
		postcodeModel.setSelectedPostcodes(postcodes);
	}
	
	public void selectArea(AreaTO area) {
		areaModel.setSelectedArea(area);
	}
	
	public void saveSelectedPostcode() {
		//TODO: implement update/insert
	}
	
	public void saveSelectedArea() {
		//TODO: implement update/insert
	}
	
	public void addPostcodesToArea() {
		AreaTO area=areaModel.getSelectedArea();
		if(area==null) {
			area=new AreaTO();
			areaModel.setSelectedArea(area);
		}
		if(postcodeModel.getSelectedPostcodes()!=null) {
			System.out.println("Service: adding postcodes to area");
			areaModel.addPostcodes(postcodeModel.getSelectedPostcodes());
			
		}
		
	}
	
}

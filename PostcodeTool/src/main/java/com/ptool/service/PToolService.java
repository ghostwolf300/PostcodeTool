package com.ptool.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ptool.db.DAOFactory;
import com.ptool.db.IAreaDAO;
import com.ptool.db.IMapDAO;
import com.ptool.db.IPostcodeDAO;
import com.ptool.model.AreaModel;
import com.ptool.model.MapModel;
import com.ptool.model.PostcodeModel;
import com.ptool.pojo.AreaStyleTO;
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
	
	public void loadAreas(int mapId) {
		List<AreaTO> areas=areaDAO.findAllMapAreas(mapId);
		for(AreaTO area : areas) {
			List<PostcodeTO> postcodes=postcodeDAO.findPostcodesByAreaId(area.getId(), mapId);
			area.setPostcodes(new HashSet<PostcodeTO>(postcodes));
		}
		areaModel.setAreas(areas);
	}
	
	public void selectPostcode(PostcodeTO postcode) {
		postcodeModel.setSelectedPostcode(postcode);
	}
	
	public void selectPostcodes(Set<PostcodeTO> postcodes) {
		postcodeModel.setSelectedPostcodes(postcodes);
	}
	
	public void selectArea(AreaTO area) {
		if(area==null) {
			System.out.println("area is null!");
		}
		areaModel.setSelectedArea(area);
	}
	
	public void saveSelectedPostcode() {
		//TODO: implement update/insert
	}
	
	public void saveArea(AreaTO area) {
		//TODO: implement update/insert
		
		if(area.isNew()) {
			//save to database
			System.out.println("Service: Creating new area");
			area.setMapId(mapModel.getMapData().getId());
			areaDAO.saveArea(area);
			areaModel.addArea(area);
		}
		else {
			if(area.getPostcodes()==null) {
				System.out.println("Service: Updating area, no postcodes!");
			}
			else {
				System.out.println("Service: Updating area");
			}
			areaDAO.saveArea(area);
			areaModel.refresh();
		}
	}
	
	public void removeArea(AreaTO area) {
		//TODO: implement delete
		areaDAO.removeArea(area.getId());
		areaModel.removeArea(area);
	}
	
	public void addPostcodesToArea() {
		AreaTO area=areaModel.getSelectedArea();
		if(area==null) {
			area=new AreaTO();
			AreaStyleTO style=new AreaStyleTO();
			area.setStyle(style);
			areaModel.setSelectedArea(area);
		}
		if(postcodeModel.getSelectedPostcodes()!=null) {
			System.out.println("Service: adding postcodes to area");
			areaModel.addPostcodes(postcodeModel.getSelectedPostcodes());
			
		}
		
	}
	
	public void addPostcodesToArea(Set<PostcodeTO> postcodes) {
		AreaTO area=areaModel.getSelectedArea();
		if(area==null) {
			area=new AreaTO();
			AreaStyleTO style=new AreaStyleTO();
			area.setStyle(style);
			areaModel.setSelectedArea(area);
		}
		System.out.println("Service: adding postcodes to area");
		areaModel.addPostcodes(postcodes);
		
	}
	
	public void showArea(AreaTO area) {
		areaModel.showArea(area);
	}
	
	public void hideArea(AreaTO area) {
		areaModel.hideArea(area);
	}
	
}

package com.ptool.service;

import java.util.List;

import com.ptool.db.DAOFactory;
import com.ptool.db.IAreaDAO;
import com.ptool.db.IPostcodeDAO;
import com.ptool.model.AreaModel;
import com.ptool.model.PostcodeModel;
import com.ptool.pojo.AreaTO;
import com.ptool.pojo.PostcodeTO;

public class PToolService {
	
	private PostcodeModel postcodeModel=null;
	private AreaModel areaModel=null;
	
	private IPostcodeDAO postcodeDAO=null;
	private IAreaDAO areaDAO=null;
	
	public PToolService() {
		postcodeModel=new PostcodeModel();
		areaModel=new AreaModel();
		
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

	public void loadPostcodes(int year) {
		List<PostcodeTO> postcodes=postcodeDAO.findAllPostcodes();
		postcodeModel.setPostcodes(postcodes);
	}
	
	public void loadAreas() {
		List<AreaTO> areas=areaDAO.findAllAreas();
		areaModel.setAreas(areas);
	}
	
	public void selectPostcode(PostcodeTO postcode) {
		postcodeModel.setSelectedPostcode(postcode);
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
	
}

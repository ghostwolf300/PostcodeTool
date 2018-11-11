package com.ptool.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.ptool.pojo.AreaTO;
import com.ptool.pojo.PostcodeTO;
import com.ptool.service.PToolService;

public class DefaultController extends AbstractController {
	
	private PToolService service=null;
	
	public DefaultController() {
		super();
		service=new PToolService();
		this.addModel(service.getMapModel());
		this.addModel(service.getPostcodeModel());
		this.addModel(service.getAreaModel());
	}
	
	public void loadMap() {
		service.loadMap(101);
	}
	
	public void loadPostcodes() {
		service.loadPostcodes(101);
	}
	
	public void loadAreas() {
		service.loadAreas(101);
	}
	
	public void setSelectedPostcode(PostcodeTO postcode) {
		service.selectPostcode(postcode);
	}
	
	public void setSelectedPostcodes(Set<PostcodeTO> pcSet) {
		service.selectPostcodes(pcSet);
	}
	
	public void setSelectedArea(AreaTO area) {
		service.selectArea(area);
	}
	
	public void addPostcodesToArea() {
		System.out.println("Controller: addPostcodesToArea");
		service.addPostcodesToArea();
	}
	
	public void saveArea(AreaTO area) {
		System.out.println("Controller: saveArea");
		service.saveArea(area);
	}
	
	public void removeArea(AreaTO area) {
		service.removeArea(area);
	}
	
	public void showArea(AreaTO area) {
		service.showArea(area);
	}
	
	public void hideArea(AreaTO area) {
		service.hideArea(area);
	}
	
}

package com.ptool.controller;

import com.ptool.service.PToolService;

public class DefaultController extends AbstractController {
	
	private PToolService service=null;
	
	public DefaultController() {
		super();
		service=new PToolService();
		this.addModel(service.getPostcodeModel());
		this.addModel(service.getAreaModel());
	}
	
	public void loadPostcodes() {
		service.loadPostcodes(2018);
	}
	
	public void loadAreas() {
		service.loadAreas();
	}
	
}

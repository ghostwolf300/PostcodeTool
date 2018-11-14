package com.ptool.controller;

import java.util.Set;

import com.ptool.pojo.CollectionTO;
import com.ptool.pojo.MapAreaTO;
import com.ptool.service.MTService;

public class DefaultController extends AbstractController {
	
	private MTService service=null;
	
	public DefaultController() {
		super();
		service=new MTService();
		this.addModel(service.getMapModel());
		this.addModel(service.getMapAreaModel());
		this.addModel(service.getCollectionModel());
	}
	
	public void loadMap() {
		service.loadMap(2);
	}
	
	public void loadPostcodes() {
		service.loadMapAreas(2);
	}
	
	public void loadAreas() {
		service.loadCollections(2);
	}
	
	public void selectMapArea(MapAreaTO postcode) {
		service.selecteMapArea(postcode);
	}
	
	public void selectMapAreas(Set<MapAreaTO> pcSet) {
		service.selectMapAreas(pcSet);
	}
	
	public void selectCollection(CollectionTO area) {
		service.selectCollection(area);
	}
	
	public void addMapAreasToCollection() {
		System.out.println("Controller: addPostcodesToArea");
		service.addMapAreasToCollection();
	}
	
	public void addMapAreasToCollection(Set<MapAreaTO> postcodes) {
		service.addMapAreasToCollection(postcodes);
	}
	
	public void saveCollection(CollectionTO area) {
		System.out.println("Controller: saveArea");
		service.saveCollection(area);
	}
	
	public void removeCollection(CollectionTO area) {
		service.removeCollection(area);
	}
	
	public void showCollection(CollectionTO area) {
		service.showArea(area);
	}
	
	public void hideCollection(CollectionTO area) {
		service.hideArea(area);
	}
	
}

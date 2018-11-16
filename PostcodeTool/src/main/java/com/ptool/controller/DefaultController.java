package com.ptool.controller;

import java.util.Set;

import com.ptool.pojo.CollectionTO;
import com.ptool.pojo.MapAreaTO;
import com.ptool.pojo.MapDataTO;
import com.ptool.pojo.MapImageParametersTO;
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
	
	public void loadMaps() {
		service.loadMaps();
	}
	
	public void showMap(MapDataTO map) {
		
		service.loadMap(map.getId());
		service.loadMapAreas(map.getId());
		service.loadCollections(map.getId());
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
		service.showCollection(area);
	}
	
	public void hideCollection(CollectionTO area) {
		service.hideCollection(area);
	}
	
	public void loadMapImage(MapImageParametersTO params) {
		service.loadMapImage(params);
	}
	
}

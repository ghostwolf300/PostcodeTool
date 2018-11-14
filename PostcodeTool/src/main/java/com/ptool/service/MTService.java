package com.ptool.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ptool.db.DAOFactory;
import com.ptool.db.ICollectionDAO;
import com.ptool.db.IMapDAO;
import com.ptool.db.IMapAreaDAO;
import com.ptool.model.CollectionModel;
import com.ptool.model.MapModel;
import com.ptool.model.MapAreaModel;
import com.ptool.pojo.CollectionStyleTO;
import com.ptool.pojo.CollectionTO;
import com.ptool.pojo.MapDataTO;
import com.ptool.pojo.MapAreaTO;

public class MTService {
	
	private MapModel mapModel=null;
	private MapAreaModel mapAreaModel=null;
	private CollectionModel collectionModel=null;
	
	private IMapDAO mapDAO=null;
	private IMapAreaDAO mapAreaDAO=null;
	private ICollectionDAO collectionDAO=null;
	
	public MTService() {
		mapModel=new MapModel();
		mapAreaModel=new MapAreaModel();
		collectionModel=new CollectionModel();
		
		mapDAO=DAOFactory.getDAOFactory(DAOFactory.DERBY2).getMapDAO();
		mapAreaDAO=DAOFactory.getDAOFactory(DAOFactory.DERBY2).getMapAreaDAO();
		collectionDAO=DAOFactory.getDAOFactory(DAOFactory.DERBY2).getCollectionDAO();
	}
	
	public MapAreaModel getMapAreaModel() {
		return mapAreaModel;
	}

	public void setMapAreaModel(MapAreaModel mapAreaModel) {
		this.mapAreaModel = mapAreaModel;
	}

	public CollectionModel getCollectionModel() {
		return collectionModel;
	}

	public void setCollectionModel(CollectionModel collectionModel) {
		this.collectionModel = collectionModel;
	}
	
	public MapModel getMapModel() {
		return mapModel;
	}

	public void setMapModel(MapModel mapModel) {
		this.mapModel = mapModel;
	}
	
	public void loadMaps() {
		List<MapDataTO> maps=mapDAO.findAllMaps();
		mapModel.setMaps(maps);
	}

	public void loadMap(int id) {
		MapDataTO mapData=mapDAO.findMapById(id);
		mapModel.setMapData(mapData);
	}

	public void loadMapAreas(int mapId) {
		List<MapAreaTO> postcodes=mapAreaDAO.findAllMapAreas(mapId);
		mapAreaModel.setMapAreas(postcodes);
	}
	
	public void loadCollections(int mapId) {
		List<CollectionTO> collections=collectionDAO.findCollectionsByMapId(mapId);
		for(CollectionTO coll : collections) {
			List<MapAreaTO> mapAreas=mapAreaDAO.findMapAreasByCollectionId(coll.getId());
			coll.setMapAreas(new HashSet<MapAreaTO>(mapAreas));
		}
		collectionModel.setCollections(collections);
	}
	
	public void selecteMapArea(MapAreaTO postcode) {
		mapAreaModel.setSelectedMapArea(postcode);
	}
	
	public void selectMapAreas(Set<MapAreaTO> postcodes) {
		mapAreaModel.setSelectedMapAreas(postcodes);
	}
	
	public void selectCollection(CollectionTO area) {
		if(area==null) {
			System.out.println("area is null!");
		}
		collectionModel.setSelectedCollection(area);
	}
	
	public void saveSelectedMapArea() {
		//TODO: implement update/insert
	}
	
	public void saveCollection(CollectionTO collection) {
		
		if(collection.isNew()) {
			//save to database
			System.out.println("Service: Creating new area");
			collection.setMapId(mapModel.getMapData().getId());
			collectionDAO.saveCollection(collection);
			collectionModel.addCollection(collection);
		}
		else {
			if(collection.getMapAreas()==null) {
				System.out.println("Service: Updating area, no postcodes!");
			}
			else {
				System.out.println("Service: Updating area");
			}
			collectionDAO.saveCollection(collection);
			collectionModel.refresh();
		}
	}
	
	public void removeCollection(CollectionTO area) {
		//TODO: implement delete
		collectionDAO.removeCollection(area.getId());
		collectionModel.removeCollection(area);
	}
	
	public void addMapAreasToCollection() {
		CollectionTO collection=collectionModel.getSelectedCollection();
		if(collection==null) {
			collection=new CollectionTO();
			CollectionStyleTO style=new CollectionStyleTO();
			collection.setStyle(style);
			collectionModel.setSelectedCollection(collection);
		}
		if(mapAreaModel.getSelectedMapAreas()!=null) {
			System.out.println("Service: adding postcodes to area");
			collectionModel.addMapAreas(mapAreaModel.getSelectedMapAreas());
			
		}
		
	}
	
	public void addMapAreasToCollection(Set<MapAreaTO> mapAreas) {
		CollectionTO collection=collectionModel.getSelectedCollection();
		if(collection==null) {
			collection=new CollectionTO();
			CollectionStyleTO style=new CollectionStyleTO();
			collection.setStyle(style);
			collectionModel.setSelectedCollection(collection);
		}
		System.out.println("Service: adding postcodes to area");
		collectionModel.addMapAreas(mapAreas);
		
	}
	
	public void showCollection(CollectionTO collection) {
		collectionModel.showCollection(collection);
	}
	
	public void hideCollection(CollectionTO collection) {
		collectionModel.hideCollection(collection);
	}
	
}

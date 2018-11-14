package com.ptool.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ptool.pojo.CollectionTO;
import com.ptool.pojo.MapAreaTO;

public class CollectionModel extends AbstractModel {
	
	public static final String P_COLLECTIONS="p_areas";
	public static final String P_SELECTED="p_selected_area";
	public static final String P_MAP_AREAS="p_area_postcodes";
	public static final String P_DISPLAYED_COLLECTIONS="p_displayed_areas";
	
	private List<CollectionTO> collections=null;
	private CollectionTO selectedCollection=null;
	private Set<CollectionTO> displayedCollections=null;
	
	public CollectionModel() {
		super();
	}

	public List<CollectionTO> getCollections() {
		return collections;
	}

	public void setCollections(List<CollectionTO> areas) {
		this.collections = areas;
		this.firePropertyChange(CollectionModel.P_COLLECTIONS, null, this.collections);
	}
	
	public void addCollection(CollectionTO area) {
		if(collections==null) {
			collections=new ArrayList<CollectionTO>();
		}
		collections.add(area);
		this.firePropertyChange(CollectionModel.P_COLLECTIONS, null, collections);
	}
	
	public void removeCollection(CollectionTO area) {
		if(collections!=null) {
			collections.remove(area);
			this.firePropertyChange(CollectionModel.P_COLLECTIONS, null, collections);
		}
	}
	
	public void showCollection(CollectionTO area) {
		if(displayedCollections==null) {
			displayedCollections=new HashSet<CollectionTO>();
		}
		displayedCollections.add(area);
		this.firePropertyChange(CollectionModel.P_DISPLAYED_COLLECTIONS, null, displayedCollections);
	}
	
	public void hideCollection(CollectionTO area) {
		displayedCollections.remove(area);
		this.firePropertyChange(CollectionModel.P_DISPLAYED_COLLECTIONS, null, displayedCollections);
	}
	
	public CollectionTO getSelectedCollection() {
		return selectedCollection;
	}

	public void setSelectedCollection(CollectionTO selectedArea) {
		this.selectedCollection = selectedArea;
		this.firePropertyChange(CollectionModel.P_SELECTED, null, this.selectedCollection);
	}
	
	public void addMapArea(MapAreaTO postcode) {
		if(selectedCollection!=null) {
			selectedCollection.addMapArea(postcode);
			this.firePropertyChange(CollectionModel.P_MAP_AREAS, null, selectedCollection.getMapAreas());
		}
	}
	
	public void addMapAreas(Set<MapAreaTO> postcodes) {
		if(selectedCollection!=null) {
			selectedCollection.addMapAreas(postcodes);
			System.out.println("CollectionModel selected postcodes: "+selectedCollection.getMapAreas().size());
			this.firePropertyChange(CollectionModel.P_MAP_AREAS, null, selectedCollection.getMapAreas());
		}
	}
	
	public void removeMapArea(MapAreaTO postcode) {
		if(selectedCollection!=null && selectedCollection.getMapAreas()!=null) {
			selectedCollection.getMapAreas().remove(postcode);
			this.firePropertyChange(CollectionModel.P_MAP_AREAS, null, selectedCollection.getMapAreas());
		}
	}
	
	public void refresh() {
		this.firePropertyChange(CollectionModel.P_COLLECTIONS, null, collections);
	}
	
}

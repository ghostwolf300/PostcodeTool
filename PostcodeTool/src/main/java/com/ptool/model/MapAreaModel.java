package com.ptool.model;

import java.util.List;
import java.util.Set;

import com.ptool.pojo.MapAreaTO;

public class MapAreaModel extends AbstractModel {
	
	public static final String P_MAP_AREAS="p_postcodes";
	public static final String P_SELECTED="p_selected_postcodes";
	
	private int year=-1;
	private List<MapAreaTO> mapAreas=null;
	private MapAreaTO selectedMapArea=null;
	private Set<MapAreaTO> selectedMapAreas=null;
	
	public MapAreaModel() {
		super();
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public List<MapAreaTO> getMapAreas() {
		return mapAreas;
	}

	public void setMapAreas(List<MapAreaTO> postcodes) {
		this.mapAreas = postcodes;
		this.firePropertyChange(MapAreaModel.P_MAP_AREAS, null , this.mapAreas);
	}

	public MapAreaTO getSelectedMapArea() {
		return selectedMapArea;
	}

	public void setSelectedMapArea(MapAreaTO selectedPostcode) {
		this.selectedMapArea = selectedPostcode;
		this.firePropertyChange(MapAreaModel.P_SELECTED, null, this.selectedMapArea);
		
	}

	public Set<MapAreaTO> getSelectedMapAreas() {
		return selectedMapAreas;
	}

	public void setSelectedMapAreas(Set<MapAreaTO> selectedPostcodes) {
		this.selectedMapAreas = selectedPostcodes;
		this.firePropertyChange(MapAreaModel.P_SELECTED, null, this.selectedMapAreas);
	}
	
	
	
	
	
}

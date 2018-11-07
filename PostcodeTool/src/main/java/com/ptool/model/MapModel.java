package com.ptool.model;

import com.ptool.pojo.MapDataTO;

public class MapModel extends AbstractModel {
	
	public static final String P_MAP_DATA="p_map_data";
	
	private MapDataTO mapData=null;
	
	public MapModel() {
		super();
	}
	
	public MapDataTO getMapData() {
		return mapData;
	}

	public void setMapData(MapDataTO mapData) {
		this.mapData = mapData;
		this.firePropertyChange(MapModel.P_MAP_DATA, null, mapData);
	}
	
}

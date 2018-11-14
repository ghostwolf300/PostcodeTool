package com.ptool.model;

import java.util.ArrayList;
import java.util.List;

import com.ptool.pojo.MapDataTO;

public class MapModel extends AbstractModel {
	
	public static final String P_MAP_DATA="p_map_data";
	public static final String P_MAPS="p_maps";
	
	private List<MapDataTO> maps=null;
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

	public List<MapDataTO> getMaps() {
		return maps;
	}

	public void setMaps(List<MapDataTO> maps) {
		this.maps = maps;
		this.firePropertyChange(MapModel.P_MAPS, null, this.maps);
	}
	
	public void addMap(MapDataTO m) {
		if(maps==null) {
			maps=new ArrayList<MapDataTO>();
		}
		maps.add(m);
		this.firePropertyChange(MapModel.P_MAPS, null, this.maps);
	}
	
	public void removeMap(MapDataTO m) {
		if(maps!=null) {
			maps.remove(m);
			this.firePropertyChange(MapModel.P_MAPS, null, this.maps);
		}
	}
	
}

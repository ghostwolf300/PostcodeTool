package com.ptool.model;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import com.ptool.pojo.MapDataTO;
import com.ptool.pojo.MapImageParametersTO;

public class MapModel extends AbstractModel {
	
	public static final String P_MAP_DATA="p_map_data";
	public static final String P_MAPS="p_maps";
	public static final String P_MAP_IMAGE="p_map_image";
	
	private List<MapDataTO> maps=null;
	private MapDataTO mapData=null;
	private MapImageParametersTO imgParams=null;
	private Image mapImage=null;
	
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

	public MapImageParametersTO getImgParams() {
		return imgParams;
	}

	public void setImgParams(MapImageParametersTO imgParams) {
		this.imgParams = imgParams;
	}

	public Image getMapImage() {
		return mapImage;
	}

	public void setMapImage(Image mapImage) {
		this.mapImage = mapImage;
		this.firePropertyChange(MapModel.P_MAP_IMAGE, null, this.mapImage);
	}
	
}

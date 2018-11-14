package com.ptool.pojo;

import java.util.HashSet;
import java.util.Set;

public class CollectionTO {
	
	private int id=-1;
	private int mapId=-1;
	private String name="<New>";
	private Set<MapAreaTO> mapAreas=null;
	private CollectionStyleTO style=null;
	private boolean selected=false;
	
	public CollectionTO() {
		
	}
	
	public CollectionTO(String name) {
		this.name=name;
	}
	
	public CollectionTO(String name, Set<MapAreaTO> postcodes) {
		this.name=name;
		this.mapAreas=postcodes;
	}
	
	public CollectionTO(CollectionTO copyArea) {
		this.mapId=copyArea.mapId;
		this.mapAreas=new HashSet<MapAreaTO>(copyArea.mapAreas);
		this.style=new CollectionStyleTO(copyArea.style);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMapId() {
		return mapId;
	}

	public void setMapId(int mapId) {
		this.mapId = mapId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<MapAreaTO> getMapAreas() {
		return mapAreas;
	}

	public void setMapAreas(Set<MapAreaTO> postcodes) {
		this.mapAreas = postcodes;
	}
	
	public void addMapArea(MapAreaTO mapArea) {
		if(mapAreas==null) {
			mapAreas=new HashSet<MapAreaTO>();
		}
		mapAreas.add(mapArea);
	}
	
	public void addMapAreas(Set<MapAreaTO> areaSet) {
		if(this.mapAreas==null) {
			this.mapAreas=new HashSet<MapAreaTO>();
		}
		this.mapAreas.addAll(areaSet);
	}
	
	public CollectionStyleTO getStyle() {
		return style;
	}

	public void setStyle(CollectionStyleTO style) {
		this.style = style;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isNew() {
		if(id==-1) {
			return true;
		}
		else {
			return false;
		}
	}
	
}

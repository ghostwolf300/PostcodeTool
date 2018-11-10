package com.ptool.pojo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AreaTO {
	
	private int id=-1;
	private int mapId=-1;
	private String name="<New>";
	private Set<PostcodeTO> postcodes=null;
	private AreaStyleTO style=null;
	private boolean selected=false;
	
	public AreaTO() {
		
	}
	
	public AreaTO(String name) {
		this.name=name;
	}
	
	public AreaTO(String name, Set<PostcodeTO> postcodes) {
		this.name=name;
		this.postcodes=postcodes;
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

	public Set<PostcodeTO> getPostcodes() {
		return postcodes;
	}

	public void setPostcodes(Set<PostcodeTO> postcodes) {
		this.postcodes = postcodes;
	}
	
	public void addPostcode(PostcodeTO postcode) {
		if(postcodes==null) {
			postcodes=new HashSet<PostcodeTO>();
		}
		postcodes.add(postcode);
	}
	
	public void addPostcodes(Set<PostcodeTO> postcodes) {
		if(this.postcodes==null) {
			this.postcodes=new HashSet<PostcodeTO>();
		}
		this.postcodes.addAll(postcodes);
	}
	
	public AreaStyleTO getStyle() {
		return style;
	}

	public void setStyle(AreaStyleTO style) {
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

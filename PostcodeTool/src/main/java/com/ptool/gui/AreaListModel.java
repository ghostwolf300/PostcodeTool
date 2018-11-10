package com.ptool.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;

import com.ptool.pojo.AreaTO;

public class AreaListModel extends AbstractListModel<AreaTO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<AreaTO> areas=null;
	
	
	public AreaListModel() {
		super();
	}
	
	
	public int getSize() {
		if(areas==null) {
			return 0;
		}
		else {
			return areas.size();
		}
	}

	public AreaTO getElementAt(int index) {
		if(areas==null) {
			return null;
		}
		else {
			return areas.get(index);
		}
	}
	
	public void setAreas(List<AreaTO> areas) {
		this.areas=areas;
		this.fireContentsChanged(this, 0, this.areas.size());
	}
	
	public List<AreaTO> getAreas() {
		return areas;
	}
	
	public void addArea(AreaTO area) {
		if(areas==null) {
			areas=new ArrayList<AreaTO>();
		}
		areas.add(area);
		this.fireContentsChanged(this, 0, this.areas.size());
	}
	
	public void removeArea(AreaTO area) {
		if(areas!=null) {
			areas.remove(area);
		}
		this.fireContentsChanged(this, 0, this.areas.size());
	}
	
}

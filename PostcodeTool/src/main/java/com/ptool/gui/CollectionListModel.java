package com.ptool.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;

import com.ptool.pojo.CollectionTO;

public class CollectionListModel extends AbstractListModel<CollectionTO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<CollectionTO> areas=null;
	
	
	public CollectionListModel() {
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

	public CollectionTO getElementAt(int index) {
		if(areas==null) {
			return null;
		}
		else {
			return areas.get(index);
		}
	}
	
	public void setAreas(List<CollectionTO> areas) {
		this.areas=areas;
		this.fireContentsChanged(this, 0, this.areas.size());
	}
	
	public List<CollectionTO> getAreas() {
		return areas;
	}
	
	public void addArea(CollectionTO area) {
		if(areas==null) {
			areas=new ArrayList<CollectionTO>();
		}
		areas.add(area);
		this.fireContentsChanged(this, 0, this.areas.size());
	}
	
	public void removeArea(CollectionTO area) {
		if(areas!=null) {
			areas.remove(area);
		}
		this.fireContentsChanged(this, 0, this.areas.size());
	}
	
}

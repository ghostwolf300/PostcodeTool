package com.ptool.gui;

import java.util.List;

import javax.swing.AbstractListModel;

import com.ptool.pojo.MapAreaTO;

public class MapAreaListModel extends AbstractListModel<MapAreaTO> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<MapAreaTO> postcodes=null;
	private MapAreaTO selectedItem;
	
	public MapAreaListModel() {
		super();
	}
	
	public List<MapAreaTO> getPostcodes() {
		return postcodes;
	}
	
	public void setPostcodes(List<MapAreaTO> postcodes) {
		this.postcodes = postcodes;
		if(this.postcodes==null) {
			this.fireContentsChanged(this, 0, 0);
		}
		else {
			this.fireContentsChanged(this, 0, this.postcodes.size()-1);
		}
	}
	
	public void remove(MapAreaTO postcode) {
		if(postcodes!=null) {
			int index=postcodes.indexOf(postcode);
			postcodes.remove(postcode);
			this.fireIntervalRemoved(this, index, index);
		}
	}

	public MapAreaTO getElementAt(int index) {
		if(postcodes!=null) {
			return postcodes.get(index);
		}
		else {
			return null;
		}
	}

	public int getSize() {
		if(postcodes==null) {
			return 0;
		}
		else {
			return postcodes.size();
		}
	}
	
	public Object getSelectedItem() {
	    return selectedItem;
	}

}

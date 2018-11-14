package com.ptool.gui;

import java.util.List;

import javax.swing.AbstractListModel;

import com.ptool.pojo.MapDataTO;

public class MapListModel extends AbstractListModel<MapDataTO> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<MapDataTO> maps=null;
	
	public MapListModel() {
		super();
	}
	
	public MapListModel(List<MapDataTO> maps) {
		super();
		this.maps=maps;
	}
	
	public MapDataTO getElementAt(int row) {
		if(maps!=null) {
			return maps.get(row);
		}
		else {
			return null;
		}
	}

	public int getSize() {
		if(maps!=null) {
			return maps.size();
		}
		else {
			return 0;
		}
	}

	public List<MapDataTO> getMaps() {
		return maps;
	}

	public void setMaps(List<MapDataTO> maps) {
		this.maps = maps;
		if(this.maps!=null) {
			this.fireContentsChanged(this, 0, this.maps.size()-1);
		}
	}

}

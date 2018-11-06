package com.ptool.model;

import java.util.List;

import com.ptool.pojo.AreaTO;

public class AreaModel extends AbstractModel {
	
	public static final String P_AREAS="p_areas";
	public static final String P_SELECTED="p_selected_area";
	
	private List<AreaTO> areas=null;
	private AreaTO selectedArea=null;
	
	public AreaModel() {
		super();
	}

	public List<AreaTO> getAreas() {
		return areas;
	}

	public void setAreas(List<AreaTO> areas) {
		this.areas = areas;
		this.firePropertyChange(AreaModel.P_AREAS, null, this.areas);
	}

	public AreaTO getSelectedArea() {
		return selectedArea;
	}

	public void setSelectedArea(AreaTO selectedArea) {
		this.selectedArea = selectedArea;
		this.firePropertyChange(AreaModel.P_SELECTED, null, this.selectedArea);
	}
	
}

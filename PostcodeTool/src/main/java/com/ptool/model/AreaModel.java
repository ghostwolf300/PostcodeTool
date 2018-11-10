package com.ptool.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.ptool.pojo.AreaTO;
import com.ptool.pojo.PostcodeTO;

public class AreaModel extends AbstractModel {
	
	public static final String P_AREAS="p_areas";
	public static final String P_SELECTED="p_selected_area";
	public static final String P_POSTCODES="p_area_postcodes";
	
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
	
	public void addArea(AreaTO area) {
		if(areas==null) {
			areas=new ArrayList<AreaTO>();
		}
		areas.add(area);
		this.firePropertyChange(AreaModel.P_AREAS, null, areas);
	}
	
	public void removeArea(AreaTO area) {
		if(areas!=null) {
			areas.remove(area);
			this.firePropertyChange(AreaModel.P_AREAS, null, areas);
		}
	}
	
	public AreaTO getSelectedArea() {
		return selectedArea;
	}

	public void setSelectedArea(AreaTO selectedArea) {
		this.selectedArea = selectedArea;
		this.firePropertyChange(AreaModel.P_SELECTED, null, this.selectedArea);
	}
	
	public void addPostcode(PostcodeTO postcode) {
		if(selectedArea!=null) {
			selectedArea.addPostcode(postcode);
			this.firePropertyChange(AreaModel.P_POSTCODES, null, selectedArea.getPostcodes());
		}
	}
	
	public void addPostcodes(Set<PostcodeTO> postcodes) {
		if(selectedArea!=null) {
			selectedArea.addPostcodes(postcodes);
			System.out.println("AreaModel selected postcodes: "+selectedArea.getPostcodes().size());
			this.firePropertyChange(AreaModel.P_POSTCODES, null, selectedArea.getPostcodes());
		}
	}
	
	public void removePostcode(PostcodeTO postcode) {
		if(selectedArea!=null) {
			
		}
	}
	
	public void refresh() {
		this.firePropertyChange(AreaModel.P_AREAS, null, areas);
	}
	
}

package com.ptool.model;

import java.util.List;

import com.ptool.pojo.PostcodeTO;

public class PostcodeModel extends AbstractModel {
	
	public static final String P_POSTCODES="p_postcodes";
	public static final String P_SELECTED="p_selected_postcode";
	
	private int year=-1;
	private List<PostcodeTO> postcodes=null;
	private PostcodeTO selectedPostcode=null;
	
	public PostcodeModel() {
		super();
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public List<PostcodeTO> getPostcodes() {
		return postcodes;
	}

	public void setPostcodes(List<PostcodeTO> postcodes) {
		this.postcodes = postcodes;
		this.firePropertyChange(PostcodeModel.P_POSTCODES, null , this.postcodes);
	}

	public PostcodeTO getSelectedPostcode() {
		return selectedPostcode;
	}

	public void setSelectedPostcode(PostcodeTO selectedPostcode) {
		this.selectedPostcode = selectedPostcode;
		this.firePropertyChange(PostcodeModel.P_SELECTED, null, this.selectedPostcode);
		
	}
	
	
	
	
	
}

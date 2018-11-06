package com.ptool.gui;

import java.util.List;

import javax.swing.AbstractListModel;

import com.ptool.pojo.PostcodeTO;

public class PostcodeListModel extends AbstractListModel<PostcodeTO> {
	
	private List<PostcodeTO> postcodes=null;
	private PostcodeTO selectedItem;
	
	public PostcodeListModel() {
		super();
	}
	
	public List<PostcodeTO> getPostcodes() {
		return postcodes;
	}

	public void setPostcodes(List<PostcodeTO> postcodes) {
		this.postcodes = postcodes;
		
	}

	public PostcodeTO getElementAt(int index) {
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

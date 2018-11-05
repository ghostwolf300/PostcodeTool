package com.ptool.db;

import java.util.List;

import com.ptool.pojo.PostcodeTO;

public interface IPostcodeDAO {
	
	public void createDB();
	public void clearTables();
	public void savePostcodes(List<PostcodeTO> postcodes);
	public PostcodeTO findPostcode(String postcode);
	public List<PostcodeTO> findAllPostcodes();
	public List<PostcodeTO> findPostcodesByAreaId(int areaId);
	
	
}

package com.ptool.db;

import java.util.List;

import com.ptool.pojo.PostcodeTO;

public interface IPostcodeDAO {
	
	public void createDB();
	public void clearTables();
	public void savePostcodes(List<PostcodeTO> postcodes);
	public PostcodeTO findPostcode(String postcode,int mapId);
	public List<PostcodeTO> findAllPostcodes(int mapId);
	public List<PostcodeTO> findPostcodesByAreaId(int areaId,int mapId);
	
	
}

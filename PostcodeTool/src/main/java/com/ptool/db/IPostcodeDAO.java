package com.ptool.db;

import java.util.List;

import com.ptool.pojo.Postcode;

public interface IPostcodeDAO {
	
	public void createDB();
	public void savePostcodes(List<Postcode> postcodes);
	
	
}

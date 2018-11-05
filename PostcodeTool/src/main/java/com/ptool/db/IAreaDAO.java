package com.ptool.db;

import java.util.List;

import com.ptool.pojo.AreaTO;

public interface IAreaDAO {
	
	public void saveArea(AreaTO area);
	public List<AreaTO> findAllAreas();
	public AreaTO findAreaById(int id);
	public void deleteArea(int id);
	
}

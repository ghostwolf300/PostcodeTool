package com.ptool.db;

import java.util.List;

import com.ptool.pojo.AreaTO;

public interface IAreaDAO {
	
	public void saveArea(AreaTO area);
	public List<AreaTO> findAllMapAreas(int mapId);
	public AreaTO findAreaById(int id);
	public void removeArea(int id);
	
}

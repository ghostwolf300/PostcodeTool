package com.ptool.db;

import com.ptool.pojo.MapDataTO;

public interface IMapDAO {
	
	public MapDataTO findMapById(int id);
	public void saveMap(MapDataTO mapData);
	
}

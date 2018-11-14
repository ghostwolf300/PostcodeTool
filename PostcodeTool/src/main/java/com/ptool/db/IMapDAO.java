package com.ptool.db;

import java.util.List;

import com.ptool.pojo.MapDataTO;

public interface IMapDAO {
	
	public List<MapDataTO> findAllMaps();
	public MapDataTO findMapById(int id);
	public void saveMap(MapDataTO mapData);
	
}

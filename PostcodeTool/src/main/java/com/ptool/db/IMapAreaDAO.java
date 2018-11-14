package com.ptool.db;

import java.util.List;

import com.ptool.pojo.MapAreaTO;

public interface IMapAreaDAO {
	
	public void createDB();
	public void clearTables();
	public void saveMapAreas(List<MapAreaTO> mapAreas);
	public MapAreaTO findMapArea(String postcode,int mapId);
	public MapAreaTO findMapArea(int id);
	public List<MapAreaTO> findAllMapAreas(int mapId);
	public List<MapAreaTO> findMapAreasByCollectionId(int areaId,int mapId);
	public List<MapAreaTO> findMapAreasByCollectionId(int collectionId);
	
	
}

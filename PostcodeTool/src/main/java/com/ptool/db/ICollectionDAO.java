package com.ptool.db;

import java.util.List;

import com.ptool.pojo.CollectionTO;

public interface ICollectionDAO {
	
	public void saveCollection(CollectionTO area);
	public List<CollectionTO> findCollectionsByMapId(int mapId);
	public CollectionTO findCollectionById(int id);
	public void removeCollection(int id);
	
}

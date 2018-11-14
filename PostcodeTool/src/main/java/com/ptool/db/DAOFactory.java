package com.ptool.db;

public abstract class DAOFactory {
	
	public static final int DERBY1=1;
	public static final int DERBY2=2;
	
	public abstract IMapDAO getMapDAO();
	public abstract IMapAreaDAO getMapAreaDAO();
	public abstract ICollectionDAO getCollectionDAO();
	
	public static DAOFactory getDAOFactory(int factory) {
		switch(factory) {
		case DERBY1 :
			return new DerbyDAOFactory();
		case DERBY2 :
			return new DerbyDAOFactory2();
		default :
			return null;
		}
	}
	
}

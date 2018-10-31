package com.ptool.db;

public abstract class DAOFactory {
	
	public static final int DERBY=1;
	
	public abstract IPostcodeDAO getPostcodeDAO();
	
	public static DAOFactory getDAOFactory(int factory) {
		switch(factory) {
		case DERBY :
			return new DerbyDAOFactory();
		default :
			return null;
		}
	}
	
}

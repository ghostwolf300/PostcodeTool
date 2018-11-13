package com.ptool.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DerbyDAOFactory2 extends DAOFactory {
	
	public static final String DRIVER="org.apache.derby.jdbc.EmbeddedDriver";
	//public static final String DB_URL="C:/Users/ville.susi/git/PostcodeTool/PostcodeTool/MapToolDB";
	public static final String DB_URL="C:/Users/ghost/git/PostcodeTool/PostcodeTool/MapToolDB";
	
	
	public DerbyDAOFactory2() {
		try {
			Class.forName(DRIVER).newInstance();
		} 
		catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Connection createConnection() {
		Connection conn=null;
		try {
			conn = DriverManager.getConnection("jdbc:derby:"+DB_URL+";create=true");
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void closeConnection() {
		try {
			DriverManager.getConnection("jdbc:derby:"+DB_URL+";shutdown=true");
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public IPostcodeDAO getPostcodeDAO() {
		return new DerbyPostcodeDAO2();
	}

	@Override
	public IAreaDAO getAreaDAO() {
		return new DerbyAreaDAO();
	}

	@Override
	public IMapDAO getMapDAO() {
		return new DerbyMapDAO();
	}

}

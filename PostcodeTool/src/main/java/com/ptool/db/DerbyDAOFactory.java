package com.ptool.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DerbyDAOFactory extends DAOFactory {
	
	public static final String DRIVER="org.apache.derby.jdbc.EmbeddedDriver";
	public static final String DB_URL="PostcodeDB";
	
	
	public DerbyDAOFactory() {
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
	
	@Override
	public IPostcodeDAO getPostcodeDAO() {
		// TODO Auto-generated method stub
		return new DerbyPostcodeDAO();
	}

}

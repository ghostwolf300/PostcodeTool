package com.ptool.db;

import java.sql.Connection;
import java.sql.SQLException;

public class DerbyPostcodeDAO implements IPostcodeDAO {

	public void createDB() {
		Connection conn=DerbyDAOFactory.createConnection();
		try {
			conn.close();
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

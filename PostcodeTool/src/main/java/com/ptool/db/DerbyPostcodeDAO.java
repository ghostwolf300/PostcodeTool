package com.ptool.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.ptool.pojo.Postcode;

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

	public void savePostcodes(List<Postcode> postcodes) {
		// TODO Auto-generated method stub
		
	}

}

package it.ttf.db.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBCon {

	public Connection getConnection() {
		Connection con = null;

		try {

			Class.forName("com.mysql.jdbc.Driver");     
			String db_url ="jdbc:mysql://localhost:3306/books",
					db_userName = "root",
					db_password = "root";
			con = DriverManager.getConnection(db_url,db_userName,db_password);  
			
			System.out.println("DB Connecting");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Database.getConnection() Error -->" + e.getMessage());
		}
		return con;
	}
	
    public void close(Connection con) {
        try {
            con.close();
        } catch (Exception ex) {
        }
    }
}
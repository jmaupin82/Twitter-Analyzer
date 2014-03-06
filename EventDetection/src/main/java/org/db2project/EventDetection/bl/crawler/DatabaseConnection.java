package org.db2project.EventDetection.bl.crawler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import oracle.jdbc.driver.OracleConnection;

public class DatabaseConnection {
	
	
	public static boolean IS_OPEN;
	/*
	 * Information regarding the database connection that we will use
	 */
	private final String DB_ADDRESS = "jdbc:oracle:thin:@//oracle.cs.ou.edu:1521/pdborcl.cs.ou.edu";
    private final String USER_NAME = "maup1282";
    private final String PASSWORD = "WTpy3Lp5";
    
    private Connection dbConnection;
	/**
	 * Default constructor
	 */
	public DatabaseConnection(){
		
		//open the database
		try {
			   this.dbConnection = (OracleConnection) DriverManager.getConnection(DB_ADDRESS,USER_NAME, PASSWORD);
			} catch (SQLException e) {
			    e.printStackTrace();
			    IS_OPEN = false;
			}
		//set isOpen to true
		IS_OPEN = true;
	}
}

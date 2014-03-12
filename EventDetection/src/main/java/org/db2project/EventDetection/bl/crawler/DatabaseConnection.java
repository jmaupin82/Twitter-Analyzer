package org.db2project.EventDetection.bl.crawler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import oracle.jdbc.driver.OracleConnection;

import org.db2project.EventDetection.dao.Tweet;

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
	
	/**
	 * 		
	 * @param t		Twitter message that should be added to the database. 
	 * @return  	True or false depending on if the connection is successful
	 */
	public boolean saveTweet(Tweet t){
		boolean success = false;
		Statement stmt;
		try {
			stmt = dbConnection.createStatement();
			ResultSet rset = stmt.executeQuery("Insert into tweet ( author, time, day, content) values ( '" + t.getAuthor() + "' , '" +
					t.getTime() + "' , '" + t.getDay() + "' , '" + t.getContent() + "')");
			success = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("there was a problem saving the query");		
			e.printStackTrace();
		}

		return success;
	}
}

package com.cs5513.persistencelayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cs5513.entities.Event;
import com.cs5513.entities.Location;

public class OracleDAL implements IDAL{

	private final String DB_URL = "jdbc:oracle:thin:@//oracle.cs.ou.edu:1521/pdborcl.cs.ou.edu";
	private final String DB_LOGIN = "maup1282";
	private final String DB_PASSWORD = "WTpy3Lp5";
	
	private Connection connection;
	
	public OracleDAL()
	{
		try {
			connection = DriverManager.getConnection(DB_URL,DB_LOGIN,DB_PASSWORD);
		} catch (SQLException e) {
			System.err.println("Connection failed!");
			e.printStackTrace();
		}
	}
	
	// TO BE COMPLETED
	@Override
	public List<Event> getAllEvents() {
		
		String query = "SELECT * FROM EVENTS";
		List<Event> events = new ArrayList<Event>();
		
		try {
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(query);
			while(result.next())
			{
				// TO BE COMPLETED
				events.add(new Event());
				System.out.println(result);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return events;
	}

	// TO BE COMPLETED
	@Override
	public List<Event> getEventsByDate(Date d) {
		String query = "SELECT * FROM EVENTS WHERE ? BETWEEN startdate AND enddate";
		List<Event> events = new ArrayList<Event>();
		
		try {
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setDate(1, new java.sql.Date(d.getTime()));
			ResultSet result = stmt.executeQuery();
			while(result.next())
			{
				// TO BE COMPLETED
				events.add(new Event());
				System.out.println(result);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return events;
	}

	@Override
	public boolean createEvent(String id, Date startDate, Date endDate,
			Location position) {
		// TODO Auto-generated method stub
		return false;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

}

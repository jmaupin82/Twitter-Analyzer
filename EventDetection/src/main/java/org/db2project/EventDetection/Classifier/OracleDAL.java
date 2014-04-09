package org.db2project.EventDetection.Classifier;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.db2project.EventDetection.Event;
import org.db2project.EventDetection.bl.crawler.TwitterTokenizer;





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
	
	/**
	 * Calls a select statement from the oracle database and packages all of the tweets into a single list.
	 * 
	 * @return		A list of all the tweets stored in the oracle database.
	 */
	public List<Tweet> getAllTweets(){
		
		String query = "SELECT * FROM tweets";
		List<Tweet> tweets = new ArrayList<Tweet>();
		
		try {
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(query);
			while(result.next())
			{
				//get the tweet information from the database
				String author = result.getString("author");
				String content = result.getString("content");
				String day = result.getString("day");
				String time = result.getString("time");
				String tokens = result.getString("tokens");
				System.out.println("tokens: "+tokens);
				tweets.add(new Tweet(author,content, tokens, day,time));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return tweets;
		
	}
	
	/**
	 *  This is a ONE-TIME migration that we run to create a table
	 *  that contains tokens for tweets.
	 */
	public void migrateTweets(){
		String query = "SELECT * FROM tweet";
		
		try {
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(query);
			String query2 = "";
			TwitterTokenizer tokenizer = new TwitterTokenizer(); 
			Statement stmtinsert;
			while(result.next())
			{
				//get the tweet information from the database
				String author = TwitterTokenizer.cleanupAuthor(result.getString("author"));
				String content = result.getString("content");
				String day = result.getString("day");
				String time = result.getString("time");
				
				// Get tokens
				List<String> tokenList = tokenizer.tokenize(content, true);
				String tokens = TwitterTokenizer.concatTokens(tokenList, ",");
				query2 = "INSERT INTO TWEETS VALUES('"
						+ author +"','"
						+ time +"','"
						+ day +"','"
						+ escapeSql(content) +"','"
						+ tokens 
						+ "')";
				
				//Insert into the new table
				System.out.println(query2);
				stmtinsert = connection.createStatement();
				stmtinsert.executeQuery(query2);
				stmtinsert.close();
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static String escapeSql(String str) {
		if (str == null) {
			return null;
		}
		return StringUtils.replace(str, "'", "''");
	}
	    
	
	// TO BE COMPLETED
	
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

	

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public boolean createEvent(String id, Date startDate, Date endDate,
			com.twitter.hbc.core.endpoint.Location position) {
		// TODO Auto-generated method stub
		return false;
	}

}

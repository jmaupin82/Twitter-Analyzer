package com.cs5513.persistencelayer;

import java.util.Date;
import java.util.List;

import com.cs5513.entities.Event;
import com.cs5513.entities.Position;
import com.cs5513.entities.Tweet;

/**
 * Interface describing what method should contain any
 * data access layer class implementing this interface
 * TO BE MODIFIED
 */
public interface IDAL {

	/**
	 * Method to get all events from database
	 * @return all the events from the database
	 */
	List<Event> getAllEvents();
	
	/**
	 * Method to get all tweets from the database
	 * @return all tweets from the database
	 */
	//List<Tweet> getAllTweets();
	
	/**
	 * Method to return all tweets from the database corresponding to the event id
	 * @param eventId the event identifier
	 * @return all the corresponding tweets
	 */
	List<Tweet> getTweetByEvent(String eventId);
	
	/**
	 * Method to get all events by location from the database
	 * @param position the position of the event
	 * @return all the events corresponding to the position
	 */
	//List<Event> getEventsByLocation(Position position);
	
	/**
	 * Method to get all the events by date from the database
	 * @param d the date of the event
	 * @return all the events corresponding to this date
	 */
	//List<Event> getEventsByDate(Date d);
	
	/**
	 * Method to get all the events by keyword from the database
	 * @param keyword one of the keyword contained by the event
	 * @return all the events that contains this keyword
	 */
	//List<Event> getEventsByKeyword(String keyword);
	
	/**
	 * Method to get all the events from the database by tweet id 
	 * @param tweetId the tweet id
	 * @return the list of event correpsonding to the tweet id
	 */
	//List<Event> getEventsByTweet(String tweetId);

	/**
	 * Method allowing to create an event
	 * @param id event id
	 * @param startDate the start date of the event
	 * @param endDate the end date of the event
	 * @param position the position of the event
	 * @return true if the event has been created, false otherwise
	 */
	boolean createEvent(String id, Date startDate, Date endDate, Position position);
	
}

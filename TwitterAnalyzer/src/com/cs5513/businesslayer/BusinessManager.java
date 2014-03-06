package com.cs5513.businesslayer;

import java.util.Date;
import java.util.List;

import com.cs5513.entities.Event;
import com.cs5513.persistencelayer.DALManager;
import com.cs5513.persistencelayer.DALProvider;

/**
 * Class allowing to manage the access and modification of data
 * as well as the call of our algorithm items
 *
 */
public class BusinessManager {

	/**
	 * BusinessManager instance
	 */
	private static BusinessManager instance = null;
	
	/**
	 * DalManager instance
	 */
	private DALManager dalManager;
	
	/**
	 * Protected constructor
	 */
	protected BusinessManager() 
	{
		dalManager = DALManager.getInstance();
		dalManager.setProvider(DALProvider.STUB);
	}
	
	/**
	 * getInstance for singleton
	 * @return instance of BusinessManager
	 */
	public static BusinessManager getInstance()
	{
		if (instance == null)
		{
			instance = new BusinessManager();
		}
		
		return instance;
	}
	
	// methods to get data items
	/**	
	 * Method asking to the data access layer to provide all the elements
	 * @return all the elements
	 */
	public List<Event> getAllEvents()
	{
		return dalManager.getAllEvents();
	}
	
	/**
	 * Method asking to the data access layer to provide all the events for a given date
	 * @param date the date where the event happens
	 */
	public List<Event> getEventsByDate(Date d)
	{
		return dalManager.getEventsByDate(d);
	}
	
	// methods to modify data items
	
	
}

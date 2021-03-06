package com.cs5513.persistencelayer;

import java.util.Date;
import java.util.List;

import com.cs5513.entities.Event;
import com.cs5513.entities.Location;


public class DALManager {

	private static DALManager instance = null;
	
	private IDAL dal;
	
	public static DALManager getInstance() {
		if (instance == null)
		{
			instance = new DALManager();
		}
		
		return instance;
	}
	
	/**
	 * Protected constructor for singleton
	 */
	protected DALManager() {}
	
	/**
	 * Method allowing to choose which "database" we want to use
	 * @param provider the type of "database"
	 */
	public void setProvider(DALProvider provider)
	{
		switch (provider) {
		case STUB:
			dal = new StubDAL() ;
			break;
		case ORACLE:
			//dal = new OracleDAL();
			break;
		case AMAZON_S3:
			
			break;
		}
	}
	
	public IDAL getDal() {
		return dal;
	}

	public void setDal(IDAL dal) {
		this.dal = dal;
	}
	
	public List<Event> getAllEvents() {
		return dal.getAllEvents();
	}

	public List<Event> getEventsByDate(Date d) {
		return dal.getEventsByDate(d);
	}
	
	public boolean createEvent(String id, Date startDate, Date endDate, Location position) {
		return dal.createEvent(id, startDate, endDate, position);
	}
	
}

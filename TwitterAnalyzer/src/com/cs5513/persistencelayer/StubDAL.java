package com.cs5513.persistencelayer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cs5513.entities.Event;
import com.cs5513.entities.Location;
import com.cs5513.entities.Tweet;

public class StubDAL implements IDAL{
	
	@Override
	public List<Event> getAllEvents() {
		List<Event> l = new ArrayList<Event>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		
		try {
			// STUB : Tweets creation
			Tweet t1 = new Tweet("00001", "Gaston", "bla bla bla bla bla bla bla", sdf.parse("03/22/2013"));
			Tweet t2 = new Tweet("00002", "Bob", "idfhvisfhsfkjjvhkjsfd", sdf.parse("03/12/2012"));
			Tweet t3 = new Tweet("00003", "Michael", "sdvhdskjvhkjdhvdfjksvh", sdf.parse("04/13/2012"));
			
			// STUB : Events creation 
			Event e1= new Event(sdf.parse("03/22/2013"));
			Event e2= new Event(sdf.parse("03/12/2012"), sdf.parse("04/13/2012"));
			e1.addTweet(t1);
			e2.addTweet(t2);
			e2.addTweet(t3);
			
			// STUB : add events in list
			l.add(e1);
			l.add(e2);
			
		} catch (ParseException e) {
			System.err.println("error while parsing date");
			e.printStackTrace();
		}
		
		
		return l;
	}

	
	public List<Event> getEventsByDate(Date d){
		List<Event> l = new ArrayList<Event>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		
		try {
			// STUB : Tweets creation
			Tweet t1 = new Tweet("00004", "Gaspar", "aeriuuhfecbndkfnc", sdf.parse("02/22/2013"));
			Tweet t2 = new Tweet("00005", "Julie", "sdjhjdbcwevihkfjv", sdf.parse("02/22/2012"));
			Tweet t3 = new Tweet("00006", "Emily", "fjsvapvnkpjnwvkfjvnfwd", sdf.parse("02/23/2012"));
			
			// STUB : Events creation 
			Event e1= new Event(sdf.parse("02/22/2013"));
			e1.addTweet(t1);
			e1.addTweet(t2);
			e1.addTweet(t3);
			
			// STUB : add events in list
			l.add(e1);
			
		} catch (ParseException e) {
			System.err.println("error while parsing date");
			e.printStackTrace();
		}
		
		
		return l;
	}

	
	
	@Override
	public boolean createEvent(String id, Date startDate, Date endDate,
			Location position) {
		// TODO Auto-generated method stub
		return false;
	}


}

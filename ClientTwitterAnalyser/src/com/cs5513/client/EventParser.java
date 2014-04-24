package com.cs5513.client;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.cs5513.client.items.EventItem;
import com.cs5513.client.items.LocationItem;
import com.cs5513.client.items.TweetItem;

public class EventParser {
	
	private static final String EVENT = "event"; 
	private static final String EVENT_ID = "id"; 
	private static final String EVENT_STARTDATE = "startDate"; 
	private static final String EVENT_ENDDATE = "endDate"; 
	private static final String EVENT_TWEETS = "tweets";
	private static final String EVENT_KEYWORDS = "keyWords";
	private static final String EVENT_LOCATION = "location";
	
	private static final String TWEET_AUTHOR = "author"; 
	private static final String TWEET_DATE = "date"; 
	private static final String TWEET_ID = "id"; 
	private static final String TWEET_MESSAGE = "message"; 
	private static final String TWEET_LOCATION= "location";
	private static final String TWEET_LOCATION_LAT= "lat";
	private static final String TWEET_LOCATION_LONG= "long";
	
	
	public static ArrayList<TweetItem> parseTweets(JSONArray array) throws JSONException
	{
		ArrayList<TweetItem> list = new ArrayList<TweetItem>();
		int arraylength = array.length();
		
		for (int i=0; i < arraylength ; ++i)
		{
			JSONObject tweet = array.getJSONObject(i);
		    String author = tweet.getString(TWEET_AUTHOR);
		    String sdate = tweet.getString(TWEET_DATE);
		    String id = tweet.getString(TWEET_ID);
		    String message =  tweet.getString(TWEET_MESSAGE);
		    LocationItem location = null;
		    
		    if (!tweet.isNull(TWEET_LOCATION))
		    {
		    	JSONObject locationObject = tweet.getJSONObject(TWEET_LOCATION);
		    	location = new LocationItem(locationObject.getDouble(TWEET_LOCATION_LAT), locationObject.getDouble(TWEET_LOCATION_LONG));
		    }
		    
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
		    
		    Date date;
			try {
				date = sdf.parse(sdate);
				list.add(new TweetItem(id,author,message,date,location));
			} catch (ParseException e) {
				System.err.println("error when parsing date");
				e.printStackTrace();
			}

		   
		}
		
		return list;
		
	}
	
	public static EventItem parseEvent(JSONObject object) throws JSONException
	{
		Date startDate = null;
		Date endDate = null;
		
		String id = object.getString(EVENT_ID);
	 	String sstartDate = object.getString(EVENT_STARTDATE);
	 	ArrayList<TweetItem> tweets = null;
	 	String sendDate = null;
	 	String keyWords = null;
	 	LocationItem location = null;
	 	
	 	/*if (!object.isNull(EVENT_TWEETS))
	 	{
	 		tweets = parseTweets(object.getJSONArray(EVENT_TWEETS));
	 	}*/
	 	
	 	if (!object.isNull(EVENT_KEYWORDS))
	 	{
	 		keyWords = object.getString(EVENT_KEYWORDS);
	 	}
	 	
	 	if (!object.isNull(EVENT_ENDDATE))
	 	{
	 		sendDate = object.getString(EVENT_ENDDATE);
	 	}
	 	
	 	if (!object.isNull(EVENT_LOCATION))
	    {
	    	JSONObject locationObject = object.getJSONObject(TWEET_LOCATION);
	    	location = new LocationItem(locationObject.getDouble(TWEET_LOCATION_LAT), locationObject.getDouble(TWEET_LOCATION_LONG));
	    }
	 	
	 	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
	    
		try {
			startDate = sdf.parse(sstartDate);
			if (sendDate != null)
			{
				endDate = sdf.parse(sendDate);
			}
		} catch (ParseException e) {
			System.err.println("error when parsing date");
			e.printStackTrace();
		}
		
		return new EventItem(id, startDate, endDate, keyWords, tweets, location);
	}
	
	public static ArrayList<EventItem> parseEvents(JSONArray array) throws JSONException
	{
		ArrayList<EventItem> list = new ArrayList<EventItem>();
		int arraylength = array.length();
		
		
		for (int i=0; i < arraylength ; ++i)
		{
			list.add(parseEvent(array.getJSONObject(i)));
		}
		
		return list;
	}
	
	public static ArrayList<EventItem> parse(JSONObject json) throws JSONException
	{
		ArrayList<EventItem> list = null;
		
		// for events
		if (!json.isNull(EVENT))
		{
			Object e = json.get(EVENT);
			
			if (e instanceof JSONObject)
			{
				JSONObject event = (JSONObject) e;
				list = new ArrayList<EventItem>();
				list.add(parseEvent(event));
				
			}
			else if (e instanceof JSONArray)
			{
				JSONArray events = (JSONArray) e;
				list = parseEvents(events);
			}
		}
		
		
		
		return list;
		
		
	}
	
}

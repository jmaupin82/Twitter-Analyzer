package com.cs5513.client.items;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class EventItem {
	
	/**
	 * Event id
	 */
	private String id;
	
	/**
	 * Event keywords
	 */
	private String keyWords;
	
	/**
	 * Tweets corresponding to the event
	 */
	private List<TweetItem> tweets = new LinkedList<TweetItem>();
	
	/**
	 * Event start date
	 */
	private Date startDate;
	
	/**
	 * Event end date
	 */
	private Date endDate;
	
	/**
	 * Event position
	 */
	private LocationItem location;
	

	
	public EventItem(String id, Date startDate, Date endDate, String keywords, List<TweetItem> tweets, LocationItem location)
	{
		this.id = id;
		this.startDate = startDate;
		this.endDate = endDate;
		this.keyWords = keywords;
		this.tweets = tweets;
		this.location = location;
	}

	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getKeyWords() {
		return keyWords;
	}
	
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}
	
	public List<TweetItem> getTweets() {
		return tweets;
	}
	
	public void setTweets(List<TweetItem> tweets) {
		this.tweets = tweets;
	}
	
	public LocationItem getLocation() {
		return location;
	}
	
	public void setLocation(LocationItem position) {
		this.location = position;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("event: ");
		sb.append(id);
		sb.append("\nstart date:");
		sb.append(startDate);
		
		if (endDate != null)
		{
			sb.append("/nend date:");
			sb.append(endDate);
		}
		
		if (location != null)
		{
			sb.append("\nlocation: [");
			sb.append(location);
			sb.append("]");
		}
		
		if (keyWords != null)
		{
			sb.append("\nkey words: [");
			sb.append(keyWords);
			sb.append("]");
		}
		
		sb.append("\ntweets:\n{");
		for(TweetItem t : tweets)
		{
			sb.append(t);
		}
		sb.append("\n\n\n}");
		
		return sb.toString();
	}
	
}

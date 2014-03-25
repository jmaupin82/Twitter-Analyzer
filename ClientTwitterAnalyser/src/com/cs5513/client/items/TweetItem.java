package com.cs5513.client.items;

import java.util.Date;


/**
 * 
 * @author Mikaël Perrin
 * Class allowing to modelize tweets items
 */
public class TweetItem {

	/**
	 * The tweet id
	 */
	private String id;
	
	
	/**
	 * The tweet author id
	 */
	private String authorId;
	
	/**
	 * The tweet message (max 140 characters)
	 */
	private String message;
	
	/**
	 * The date and time of the tweet 
	 */
	private Date date;
	
	/**
	 * The possible position of the tweet
	 * (Not mandatory)
	 */
	private LocationItem location;
	
	
	/**
	 * tweet item constructor
	 * @param id the tweet id
	 * @param author the tweet author id
	 * @param message the tweet message
	 * @param date the date of publication of the tweet
	 * @param position position of the tweet (if not specified use the other constructor)
	 */
	public TweetItem(String id, String authorId, String message, Date date, LocationItem  location)
	{
		this.id = id;
		this.date = date;
		this.authorId = authorId;
		this.message = message;
		this.location = location;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getAuthor() {
		return authorId;
	}
	
	public void setAuthor(String authorId) {
		this.authorId = authorId;
	} 
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public LocationItem getLocation() {
		return location;
	}
	
	public void setLocation(LocationItem position) {
		this.location = position;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("tweet:");
		sb.append(id);
		sb.append("\nauthor:");
		sb.append(authorId);
		sb.append("\ndate:");
		sb.append(date);
		sb.append("\nmessage:");
		sb.append(message);
		
		if (location != null)
		{
			sb.append("\nlocation: [");
			sb.append(location);
			sb.append("]");
		}
		
		
		return sb.toString();
	}
	
}

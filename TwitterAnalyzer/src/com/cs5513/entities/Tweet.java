package com.cs5513.entities;

import java.util.Date;


/**
 * 
 * @author Mikaël Perrin
 * Class allowing to modelize tweets
 */
public class Tweet {

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
	private Location position;
	
	public Tweet()
	{
		
	}
	
	/**
	 * First tweet constructor, to be used without position
	 * @param id the tweet id
	 * @param author the tweet author id
	 * @param message the tweet message
	 * @param date the date of publication of the tweet
	 */
	public Tweet(String id, String authorId, String message, Date date)
	{
		this.id = id;
		this.date = date;
		this.authorId = authorId;
		this.message = message;
		this.position = new Location();
	}
	
	/**
	 * Second tweet constructor
	 * @param id the tweet id
	 * @param author the tweet author id
	 * @param message the tweet message
	 * @param date the date of publication of the tweet
	 * @param position position of the tweet (if not specified use the other constructor)
	 */
	public Tweet(String id, String authorId, String message, Date date, Location position)
	{
		this(id,authorId,message,date);
		this.position = position;
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
	
	public Location getPosition() {
		return position;
	}
	
	public void setPosition(Location position) {
		this.position = position;
	}

	
}

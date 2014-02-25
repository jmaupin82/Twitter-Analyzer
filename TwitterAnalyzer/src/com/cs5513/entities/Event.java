package com.cs5513.entities;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Event {
	
	/**
	 * Event class Ids
	 */
	private static int ids = 0;
	
	/**
	 * Event id
	 */
	private String id;
	
	/**
	 * Event keywords
	 */
	private List<String> keyWords = new LinkedList<String>();
	
	/**
	 * Tweets corresponding to the event
	 */
	private List<Tweet> tweets = new LinkedList<Tweet>();
	
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
	private Position position;
	
	/**
	 * Default constructor, requiered by servlets
	 */
	public Event()
	{
		
	}
	
	public Event(Date startDate)
	{
		id = String.valueOf(++ids);
		this.startDate = startDate;
	}
	
	public Event(Date startDate, Date endDate)
	{
		this(startDate);
		this.endDate = endDate;
	}
	
	public Event(Date startDate, Date endDate, Position position)
	{
		this(startDate);
		this.endDate = endDate;
		this.position = position;
	}
	
	/**
	 * method to add a keyword inside the list of keywords related to the event
	 * @param keyWord the keyword to be added
	 * @return true if insertion ok, false otherwse
	 */
	public boolean addKeyWord(final String keyWord)
	{
		return keyWords.add(keyWord);
	}
	
	/**
	 * method allowing to add several keywords inside the list of keyword related to the event
	 * @param keyWords the keyWords to be added
	 * @return true if insertion ok, falae otherwise
	 */
	public boolean addKeywords(final List<String> keyWords)
	{
		return keyWords.addAll(keyWords);
	}
	
	/**
	 * method to add a tweet inside the list of tweets related to the event
	 * @param tweet the tweet to be added
	 * @return true if insertion ok, false otherwse
	 */
	public boolean addTweet(final Tweet tweet)
	{
		return tweets.add(tweet);
	}
	
	/**
	 * method allowing to add several tweets inside the list of tweets related to the event
	 * @param tweet the tweet to be added
	 * @return true if insertion ok, falae otherwise
	 */
	public boolean addTweets(final List<Tweet> tweets)
	{
		return tweets.addAll(tweets);
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public List<String> getKeyWords() {
		return keyWords;
	}
	
	public void setKeyWords(List<String> keyWords) {
		this.keyWords = keyWords;
	}
	
	public List<Tweet> getTweets() {
		return tweets;
	}
	
	public void setTweets(List<Tweet> tweets) {
		this.tweets = tweets;
	}
	
	public Position getPosition() {
		return position;
	}
	
	public void setPosition(Position position) {
		this.position = position;
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
	
	
	
}

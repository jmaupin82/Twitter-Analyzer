package org.db2project.EventDetection.dao;

/*
 * Loosely based on the sql table with code
 * 
 * create table tweet (
  author VARCHAR2(30),
  time varchar2(5),
  day VARCHAR2(6),
  content VARCHAR2(144)
  );
 * 
 */
public class Tweet {
	private String author;
	private String time;
	private String content;
	private String day;


	/**
	 * Only Constructor for our Tweet class
	 * @param author		username of the author of the tweet
	 * @param day			Day the tweet was sent on
	 * @param time			Time of day in 24 hour format, eg) 21:45. 
	 * @param content		Content of the tweet; the message including all hashtags. 
	 */
	public Tweet( String author, String day, String time, String content){
		this.author = author;
		if(author.length() > 30){
			this.author = author.substring(0, 30);
		}
		this.time = time;
		this.content = content;
		this.day = day;
	}

	public String getContent(){
		return content;
	}
	public String getAuthor(){
		return author;
	}
	public String getTime(){
		return time;
	}

	public String getDay(){
		return day;
	}

}

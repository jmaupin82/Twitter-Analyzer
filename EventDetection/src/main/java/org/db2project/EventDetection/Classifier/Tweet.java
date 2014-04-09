package org.db2project.EventDetection.Classifier;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;


public class Tweet {
	/** The message in the tweet */
	private String content;
	/** The author of the tweet */
	private String author;
	/** The tokens in the message of the tweet, excluding stop words */
	private Set<String> tokens;
	/** The hashtags within the tweet */
	private Set<String> hashTags;
	/** The date on which the tweet was posted */
	private Calendar date;
	
	/** Old data fields that should eventually be destroyed but can't be just yet */
	private String day;
	private String time;
	
	public Tweet() {
		
	}
	
	/**
	 * 
	 * @param content
	 * @param tokens
	 * @param hashTags
	 * @param date
	 */
	public Tweet(String content, Set<String> tokens, Set<String> hashTags,
			     Calendar date) {
		this.content  = content;
		this.tokens   = tokens;
		this.hashTags = hashTags;
		this.date     = date;
	}
	
	public Tweet(String author, String content, String tokenString, String day, String time){
		this.content = content;
		this.author = author;
		this.tokens = buildTokens(tokenString);
		this.hashTags = buildTags();
		this.day = day;
		this.time = time;
	}
	
	
	public String getContent() {
		return content;
	}

	public Set<String> getTokens() {
		return tokens;
	}
	
	public String getTokensString(){
		StringBuilder sb = new StringBuilder();
		String sep = "";
		for(String t: tokens){
			sb.append(sep).append(t);
			sep = ",";
		}
		
		return sb.toString();
	}
	
	public Set<String> getHashTags() {
		return hashTags;
	}

	public Calendar getDate() {
		return date;
	}

	public int getNumHashTags() {
		return hashTags.size();
	}
	public String getDay() {
		return day;
	}
	public String getTime(){
		return time;
	}
	public String getAuthor() {
		return author;
	}
	
	/**
	 * Returns true iff the given word is a hash tag.
	 * @param word
	 * @return
	 */
	public boolean wordIsHashTag(String word) {
		return hashTags.contains(word);
	}
	
	/**
	 * Returns true iff, the given word occurs as a token.
	 * @param word
	 * @return
	 */
	public boolean wordOccurs(String word) {
		return tokens.contains(word);
	}
	
	public int countWordOccurrence(String word) {
		int count = 0;
		
		
		return count;
	}
	/**
	 * Build a set of strings that is all of the hashtags in the content of the tweet
	 * 
	 * @return Set of HashTags
	 */
	private HashSet<String> buildTags(){
		HashSet<String> results = new HashSet<String>();
		
		String[] temp = this.content.split("#");
		for(int i = 1; i < temp.length; ++i){
			int end = temp[i].indexOf(' ');
			if(end > 0 ){
				results.add(temp[i].substring(0, end));
			}else{
				//the tweet must end with the hashtag.
				//just add the string
				results.add(temp[i]);
			}
		}
		return results;
	}
	
	private Set<String> buildTokens(String tokenString){
		HashSet<String> results = new HashSet<String>();
		
		if(tokenString != null){
			String[] words = tokenString.split(",");
			for(String w : words){
				results.add(w);
			}
		}
		return results;
	}


}

package org.db2project.EventDetection.Classifier;
import java.util.Calendar;
import java.util.Set;


public class Tweet {
	/** The message in the tweet */
	private String content;
	/** The tokens in the message of the tweet, excluding stop words */
	private Set<String> tokens;
	/** The hashtags within the tweet */
	private Set<String> hashTags;
	/** The date on which the tweet was posted */
	private Calendar date;
	
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
	
	
	public String getContent() {
		return content;
	}

	public Set<String> getTokens() {
		return tokens;
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
}

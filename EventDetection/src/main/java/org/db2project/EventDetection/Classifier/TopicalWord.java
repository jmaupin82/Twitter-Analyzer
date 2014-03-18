package org.db2project.EventDetection.Classifier;
import java.util.HashSet;

/**
 * This class models a topical word.
 * 
 * @author eleal
 *
 */
public class TopicalWord {
	private String word;
	
	/** The set of tweets in which it occurs */
	private HashSet<Tweet> tweetsWhereOccurs;
	
	public TopicalWord(String word){
		this.word = word;
	}

	public String getWord() {
		return word;
	}
	
	public HashSet<Tweet> getTweetsWhereOccurs() {
		return tweetsWhereOccurs;
	}
	
	
}

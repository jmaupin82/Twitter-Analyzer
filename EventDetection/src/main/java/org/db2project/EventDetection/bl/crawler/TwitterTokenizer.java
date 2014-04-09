package org.db2project.EventDetection.bl.crawler;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

public class TwitterTokenizer {
	private Tokenizer tokenizer;
	private static List<String> StopWordsList;
	
	public TwitterTokenizer(){
		initializeTokenizer();
	}
	
	private void initializeTokenizer(){		
		try {
			InputStream in = new FileInputStream("resources/en-token.bin");
			TokenizerModel model = new TokenizerModel(in);
			tokenizer = new TokenizerME(model);
			
			getStopWordsList("resources/stopwords.txt");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
		}
	}
	
	/**
	 * Load the topical words list file in an global array.
	 * @param filename
	 */
	private static void getStopWordsList(String filepath){
		try {
			StopWordsList = Files.readAllLines(Paths.get(filepath), StandardCharsets.UTF_8);
			
			// Additional stop words from our processing of tweets.
			StopWordsList.add("AT_USER");
			StopWordsList.add("URL");
			// Remove twitter generated keyword RT for re-tweets
			StopWordsList.add("rt");
		}
		catch (IOException e){
			e.printStackTrace();
		}	
	}
	
	/**
	 * Get a list of tokens for a given chunk of text
	 * @param tweetContent
	 * @return
	 */
	public List<String> tokenize(String tweetContent, boolean removeStopWords){
		
		if (tokenizer == null) {
			return null;
		}
		
		// process tweet
		String tweet = processTweet(tweetContent); 
		
		// Generate a token of words
		String [] words = tokenizer.tokenize(tweet);
		List<String> tokens = new ArrayList<String>();
		
		for(String w : words){
			// strip punctuation
			w = w.replaceAll("(\\.|,|\\?|')+", "");
			
			// check if the word stats with an alphabet
			if(w.matches("^[a-zA-Z][a-zA-Z0-9]*$")){
				tokens.add(w);
			}	
		}
		
		if( removeStopWords) {
			// remove all stop words
			tokens.removeAll(StopWordsList);
		}
	
		return tokens;
	}
	
	/**
	 * 
	 * @param strings
	 * @param separator
	 * @return
	 */
	public static String concatTokens(List<String> strings, String separator) {
	    StringBuilder sb = new StringBuilder();
	    String sep = "";
	    for(String s: strings) {
	        sb.append(sep).append(s);
	        sep = separator;
	    }
	    return sb.toString();                           
	}
	
	public static String processTweet(String tweetContent){
		// Convert to lower case
		String tweet = tweetContent.toLowerCase();
		
		// Convert www.* or https?://* to URL
		tweet = tweet.replaceAll("((www\\.[\\s]+)|(https?://[^\\s]+))", "URL");
		
		// Convert @username to AT_USER
	    tweet = tweet.replaceAll("@[^\\s]+","AT_USER");
	    
	    // Remove additional white spaces
	    tweet = tweet.replaceAll("[\\s]+", " ");
	    
	    // Replace #hashtag with hashtag
	    tweet = tweet.replaceAll("#([^\\s]+)", "$1");
	    
	    // trim
	    tweet = tweet.trim();
	    
	    /* Optional Filtering */
	    // replace all repeating characters added to stress importance.
	    tweet = replaceTwoOrMore(tweet);
		
		return tweet;
	}
	
	public static String replaceTwoOrMore(String tweet){
		/**
		 * We look for 2 or more repetitive letters in words and
		 * replace them by 2 of the same.
		 * Use case: E.g. hunggrryyy, huuuuuuungry for 'huungry'. 
		 */
		
		return tweet.replaceAll("(.)\\1{1,}", "$1$1");
	}
	
	public static String extractHashtags(String tweet){
		Pattern pattern = Pattern.compile("#([^\\s]+)");
		
		Matcher matcher = pattern.matcher(tweet);
		StringBuilder sb = new StringBuilder();
		String sep = "";
		String separator = ",";
		while(matcher.find()){
			sb.append(sep).append(matcher.group(1));
	        sep = separator;
		}
		
		System.out.println("hashtags: " + sb.toString());
		return sb.toString();
	}
	
	public static String cleanupAuthor(String author){
		if(author == null){
			return null;
		}
		return author.replaceAll("[^a-zA-Z0-9_-]", "");
	}
}

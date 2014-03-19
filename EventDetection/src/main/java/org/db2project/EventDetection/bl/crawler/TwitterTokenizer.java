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
		
		// Generate a token of words
		String [] words = tokenizer.tokenize(tweetContent);
		List<String> tokens = new ArrayList<String>(Arrays.asList(words));
		
		if( removeStopWords) {
			// remove all stop words
			tokens.removeAll(StopWordsList);
		}
	
		return tokens;
	}
	
	public static String preProcessTweet(String tweetContent){
		// TODO: Fill in preprocessing parser rules.
		// Examples: http://ravikiranj.net/drupal/201205/code/machine-learning/how-build-twitter-sentiment-analyzer
		return tweetContent;
	}
}

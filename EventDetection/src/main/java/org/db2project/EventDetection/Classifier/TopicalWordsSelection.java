package org.db2project.EventDetection.Classifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


public class TopicalWordsSelection {

	/**
	 * This function calculates the document frequency of a given word.
	 * 
	 * @param word
	 *          The term whose frequency we want to calculate.
	 * @param documents
	 *          The set of documents over which we want to calculate
	 *          the document frequency.
	 * @return
	 */
	private static float getDocumentFrequency(String word, Set<Tweet> documents){
		int count = 0;
		
		for(Tweet tweet: documents){
			count += (tweet.wordOccurs(word) ? 1 : 0);
		}
		
		return count / ((float) documents.size());
	}
	
	/**
	 * Given a set of documents, this method returns the average document
	 * frequency of all tokens in those documents.
	 * We assume that the set of tweets is composed of tweets that were
	 * POSTED ON THE SAME date.
	 *  
	 * @param documents
	 * @return
	 */
	private static float getAverageDocumentFrequency(Set<Tweet> documents) {
		int totalFrequency = 0;

		// For every token in every tweet.
		for(Tweet tweet: documents){
			for(String token: tweet.getTokens()) {
				totalFrequency += getDocumentFrequency(token, documents);
			}
		}
		
		return totalFrequency / ((float) documents.size());
	}
	
	/**
	 * Given a set of documents, this method returns the average document
	 * frequency of all tokens in those documents.
	 * We assume that the set of tweets is composed of tweets that were
	 * POSTED ON THE SAME date.
	 * 
	 * This method stores the token document frequencies in tokenFrequency.
	 *
	 * @param documents
	 * @param tokenFrequency
	 * @return
	 */
	private static float getAverageDocumentFrequency(Set<Tweet> documents,
			HashMap<String, Float> tokenFrequencies) {
		int totalFrequencies = 0;

		// For every token in every tweet.
		for(Tweet tweet: documents){
			for(String token: tweet.getTokens()) {
				float tokenDocumentFrequency = getDocumentFrequency(token, 
						documents);
				totalFrequencies += tokenDocumentFrequency; 
				tokenFrequencies.put(token, tokenDocumentFrequency);
			}
		}
		
		return totalFrequencies / ((float) documents.size());
	}
	
	/**
	 * Given a word and a set of tweets, this function measures the number of
	 * times that word occurs as a hashtag, divided by the total number of
	 * hashtags.
	 * 
	 * @param word
	 * @param documents
	 * @return
	 */
	private static float getWordProbabilityInHashTags(String word, 
			Set<Tweet> documents) {
		int count = 0;
		int totalNumHashTags = 0;
		
		for(Tweet tweet: documents){
			count += (tweet.wordIsHashTag(word) ? 1 : 0);
			totalNumHashTags += tweet.getNumHashTags();
		}
	
		return count / ((float) totalNumHashTags);
	}
	
	/**
	 * Given a word and a set of tweets, this function measures the number of
	 * times that word occurs as a hashtag, divided by the total number of
	 * hashtags.
	 * 
	 * @param documents
	 * @param tokenFrequencies
	 * @return
	 */
	private static float getAverageHashTagProbability(
			Set<Tweet> documents, HashMap<String, Float> tokenHashTagProbabilities) {
		// This counts the number of tokens.
		int numTokens = 0;
		// This will hold the sum of the hashtag probabilities of all tokens.
		int totalHashTagProbabilities = 0;
		
		// For every token in every tweet.
		for(Tweet tweet: documents){
			for(String token: tweet.getTokens()) {
				float tokenHashTagProbability = getWordProbabilityInHashTags(token, 
						documents);
				
				tokenHashTagProbabilities.put(token, tokenHashTagProbability);
				totalHashTagProbabilities += tokenHashTagProbability;
				numTokens++;
			}
		}
		
		return totalHashTagProbabilities / ((float)numTokens);
	}
	
	/**
	 * Given a set of tweets, this method returns a set of topical words.
	 * We assume that the set of tweets is composed of tweets that were
	 * POSTED ON THE SAME date.
	 * 
	 * We consider that a word is a topical word if:
	 *    - 
	 * 
	 * @param documents
	 * @return
	 */
	public static Set<String> selectTopicalWords(Set<Tweet> documents) {
		// These are only candidates to be topical words.
		HashSet<String> candidateTopicalWords   = new HashSet<String>();
		// This will be the result set.
		HashSet<String> topicalWords            = new HashSet<String>();
		
		
		/*
		 *  1. Filter those tokens whose document frequencies are too low.
		 *  
		 */
		// This map will contain for each token, its document frequency.
		HashMap<String, Float> tokenFrequencies = new HashMap<String,Float>();		
		float averageDocumentFrequency = getAverageDocumentFrequency(documents,
				tokenFrequencies);
		
		for(Tweet tweet: documents){
			for(String token: tweet.getTokens()) {
				if(tokenFrequencies.get(token)>= averageDocumentFrequency) {
					candidateTopicalWords.add(token);
				}
			}
		}
		
		/*
		 *  3. Filter those tokens whose probability of occurrence in hashtags
		 *   is too low.
		 */	
		// This map will contain for each token, its document frequency.
		HashMap<String, Float> tokenHashTagProbabilities = new HashMap<String,Float>();
		float averageHashTagProbability = getAverageHashTagProbability(documents,
				tokenHashTagProbabilities);
		
		for(Tweet tweet: documents){
			for(String token: tweet.getTokens()) {
				if(tokenHashTagProbabilities.get(token)< averageHashTagProbability) {
					candidateTopicalWords.remove(token);
				}
			}
		}
		
		
		/*
		 *  4. Filter those tokens whose entropy is too high.
		 */	
		
		
		return topicalWords;
	}
	
	
	
	
}

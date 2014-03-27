package org.db2project.EventDetection.Classifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.apache.lucene.document.Document;


/**
 * This class implements the methods that are needed to select
 * topical words out of the set of all tweets contained in the
 * Lucene index.
 * 
 * @author eleal
 *
 */
public class TopicalWordsSelection {
	/** This is an instance of the interface to our Lucene index. */
	private IndexManager indexManager;
		
	public TopicalWordsSelection() {
		indexManager = IndexManager.getInstance();
	}
	
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
	private float getDocumentFrequency(String word){
		return indexManager.getDocumentFrequency(word);
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
	private float getAverageDocumentFrequency() {
		int totalFrequencies = 0;
		
		Document doc;
		// For every token in every tweet.
		while( (doc = indexManager.getDocIterator().next()) != null){
			for(String token: doc.get(IndexManager.TOKENS_FIELD).split(",")) {
				float tokenDocumentFrequency = 
						getDocumentFrequency(token);
				totalFrequencies += tokenDocumentFrequency; 
			}
		}
		return totalFrequencies / ((float) indexManager.getTotalNumberOfDocuments());
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
	private float getAverageDocumentFrequency(
			HashMap<String, Float> tokenFrequencies) {
		int totalFrequencies = 0;

		Document doc;
		// For every token in every tweet.
		while( (doc = indexManager.getDocIterator().next()) != null){
			for(String token: doc.get(IndexManager.TOKENS_FIELD).split(",")) {
				float tokenDocumentFrequency = getDocumentFrequency(token);
				totalFrequencies += tokenDocumentFrequency; 
				tokenFrequencies.put(token, tokenDocumentFrequency);
			}
		}
		
		return totalFrequencies / ((float) indexManager.getTotalNumberOfDocuments());
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
	private float getWordProbabilityInHashTags(String word) {
		int count = 0;
		int totalNumHashTags = 0;
				
		Document doc;
		// For every token in every tweet.
		while( (doc = indexManager.getDocIterator().next()) != null){
			for(String token: doc.get(IndexManager.HASHTAG_FIELD).split(",")) {
				if(word.equals(token)) {
					count++;
				}
				// Since totalNumHashTags has the sum of the number
				// of hashtags in all tweets, then if we add 1 every
				// iteration we would get that value.
				totalNumHashTags += 1;
			}
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
	private float getAverageHashTagProbability(
			HashMap<String, Float> tokenHashTagProbabilities) {
		// This counts the number of tokens.
		int numTokens = 0;
		// This will hold the sum of the hashtag probabilities of all tokens.
		int totalHashTagProbabilities = 0;
		
		
		Document doc;
		// For every token in every tweet.
		while( (doc = indexManager.getDocIterator().next()) != null){
			for(String token: doc.get(IndexManager.TOKENS_FIELD).split(",")) {

				float tokenHashTagProbability = getWordProbabilityInHashTags(token);
				tokenHashTagProbabilities.put(token, tokenHashTagProbability);
				totalHashTagProbabilities += tokenHashTagProbability;
				numTokens++;
			}
		}
		
		// For every token in every tweet.
		while( (doc = indexManager.getDocIterator().next()) != null){
			for(String token: doc.get(IndexManager.TOKENS_FIELD).split(",")) {
				float tokenHashTagProbability = getWordProbabilityInHashTags(token);
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
	public  Set<String> selectTopicalWords( ) {
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
		float averageDocumentFrequency = getAverageDocumentFrequency(
				tokenFrequencies);
		
		
		Document doc;
		// For every token in every tweet.
		while( (doc = indexManager.getDocIterator().next()) != null){
			for(String token: doc.get(IndexManager.TOKENS_FIELD).split(",")) {
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
		float averageHashTagProbability = 
				getAverageHashTagProbability(tokenHashTagProbabilities);

		// For every token in every tweet.
		while( (doc = indexManager.getDocIterator().next()) != null){
			for(String token: doc.get(IndexManager.TOKENS_FIELD).split(",")) {
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

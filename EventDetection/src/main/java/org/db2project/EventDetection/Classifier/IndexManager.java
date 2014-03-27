package org.db2project.EventDetection.Classifier;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.FSDirectory;

/**
 * The index manager is the class in charge of presenting an interface
 * to the Apache Lucene index.
 * 
 * This class will be a singleton.
 * 
 * @author eleal
 *
 */
public class IndexManager {
	/** We use this attribute to go through all the documents in the index*/
	private DocumentIterator docIterator;
	
	/** This is used for the document iterator. */
	private IndexReader indexReader;

	/** This attribute is the singleton instance. */
	private static IndexManager instance = null;
	
	/** This is the path where the index is stored. */
	private static final String PATH = "";
		
	public IndexManager(String indexPath) {
		indexReader = null;
		try {
			indexReader = DirectoryReader.open(FSDirectory.open(new File(indexPath)));
			docIterator = new DocumentIterator(indexReader);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Given a word, returns the number of tweets in which it
	 * occurs.
	 * 
	 * @param word
	 * @return
	 */
	public int getCountWordOccurrences(String word) {
		// TODO 
		return 0;
	}

	/**
	 * Given a word it returns the fraction of the number of
	 * tweets in which the word occurs, divided by the total
	 * number of tweets.
	 * 
	 * @param word
	 * @return
	 */
	public int getDocumentFrequency(String word) {
		// TODO
		return 0;
	}

	/**
	 * Returns the total number of documents.
	 * 
	 * @return
	 */
	public int getTotalNumberOfDocuments() {
		return indexReader.numDocs();
	}

	/**
	 * This method returns an iterator to go through all the 
	 * documents in the index.
	 * 
	 * @return
	 */
	public DocumentIterator getDocIterator() {
		return docIterator;
	}


	public static IndexManager getInstance() {
		if(instance != null) {
			instance = new IndexManager(PATH);
		}
		return instance;
	}
}

package org.db2project.EventDetection.Classifier;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;



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

	private IndexWriter indexWriter;

	/** This attribute is the singleton instance. */
	private static IndexManager instance = null;

	/** This is the path where the index is stored. */
	private static final String PATH = "customIndex/";

	/**
	 * Construct an empty IndexManager
	 */
	public IndexManager(){
		SimpleAnalyzer analyzer = new SimpleAnalyzer(Version.LUCENE_35);  
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_35, analyzer);
		
		try {
			this.indexWriter = new IndexWriter(FSDirectory.open(new File(PATH)), indexWriterConfig);
		
		//first ask the database to give me all of the tweets.
		OracleDAL db = new OracleDAL();
		long bob = System.currentTimeMillis();
		ArrayList<Tweet> list = (ArrayList<Tweet>) db.getAllTweets();
		long bob1 = System.currentTimeMillis();
		System.out.println("Retrieved all the tweets in "  + (bob1 - bob)/1000 + " seconds.");

		//now build the index
		bob = System.currentTimeMillis();
		int indexedDocumentCount = this.indexDocsFromList(indexWriter, list); 
		long bob2 = System.currentTimeMillis();
		indexWriter.close();  
		System.out.println(indexedDocumentCount + " records have been indexed successfully in " + (bob2-bob)/1000 +" seconds");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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
	
	public int indexDocsFromList(IndexWriter writer, ArrayList<Tweet> list) throws Exception{
		for(Tweet t : list){
			Document d = new Document();  
		     d.add(new Field("message", t.getContent(), Field.Store.YES, Field.Index.ANALYZED));
//		     d.add(new Field("author" , rs.getString("author"),Field.Store.YES, Field.Index.ANALYZED));
		     
		     d.add(new Field("Date", t.getDay(), Field.Store.YES, Field.Index.ANALYZED)); 
		    
		     writer.addDocument(d);
		}
		return list.size();
	}

	/**
	 * Main method just for testing.
	 * 
	 * @param args command line args
	 */
	public static void main(String args[]){
		IndexManager im = new IndexManager();

	}
}

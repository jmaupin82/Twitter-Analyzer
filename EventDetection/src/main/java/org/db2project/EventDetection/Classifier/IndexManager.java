package org.db2project.EventDetection.Classifier;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
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
	
	private IndexSearcher indexSearcher;

	/** This attribute is the singleton instance. */
	private static IndexManager instance = null;

	/** This is the path where the index is stored. */
	private static final String PATH = "customIndex/";

	/** The name of the field of a document (in lucene) that has the 
	 * hashtags. */
	public static final String HASHTAG_FIELD = "hashtag";

	/** The name of the field of a document (in lucene) that has the 
	 * tokens. */
	public static final String TOKENS_FIELD  = "tokens";


	/**
	 * Construct an empty IndexManager
	 */
	public IndexManager(){
		SimpleAnalyzer analyzer = new SimpleAnalyzer(Version.LUCENE_47);  
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_47, analyzer);

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
			indexSearcher = new IndexSearcher(indexReader);
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
	 * @throws IOException 
	 */
	public long getCountWordOccurrences(String word) {
		Term termInstance = new Term(TOKENS_FIELD, word); 
		long tweetCount = -1;

		try {
			tweetCount = indexReader.docFreq(termInstance);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return tweetCount;
	}

	/**
	 * Given a word it returns the fraction of the number of
	 * tweets in which the word occurs, divided by the total
	 * number of tweets.
	 * 
	 * @param word
	 * @return
	 */
	public float getDocumentFrequency(String word) {
		long tweetCount = getCountWordOccurrences(word);

		// Get the total number of tweets in the index.
		int totalNumberOfTweets = indexReader.numDocs();

		return (float)tweetCount/(float)totalNumberOfTweets;
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
	 * Given 2 strings (most likely they are topical words),
	 * count the number of tweets (documents) in which <both>
	 * of them occur.
	 * 
	 * @param word1
	 * @param word2
	 * @return
	 * 
	 * 
	 */
	public int countCoOccurrences(String word1, String word2) {
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_47);
		QueryParser parser = new QueryParser(Version.LUCENE_47, TOKENS_FIELD, analyzer);
		// We want to search all documents where BOTH words occur.
		String line = word1 + " AND " + word2;
		
		Query query = null;
		try {
			query = parser.parse(line);
			int maxHits = indexReader.numDocs();
			
			TopDocs hits;
			
			hits = indexSearcher.search(query,maxHits);
			
			ScoreDoc[] scoreDocs = hits.scoreDocs;
			
			System.out.println("Coocurrence score: "+ scoreDocs.length);
			System.out.println("total docs count: "+ maxHits);
			
			return scoreDocs.length;
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Coocurrence score: "+ -1);
		return -1;
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
		if(instance == null) {
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

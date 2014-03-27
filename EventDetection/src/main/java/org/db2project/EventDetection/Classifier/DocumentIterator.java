package org.db2project.EventDetection.Classifier;

import java.io.IOException;
import java.util.Iterator;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;

/**
 * 
 * @author eleal
 *
 */
public class DocumentIterator implements Iterator<Document> {

	/** The first index that has not been returned. */
	int currentIndex;

	IndexReader indexReader;

	/**
	 * 
	 * @param indexReader
	 */
	public DocumentIterator(IndexReader indexReader) {
		currentIndex = 0;
		this.indexReader = indexReader;
	}

	/**
	 * Check if there is another result to return.
	 */
	public boolean hasNext() {
		return currentIndex < indexReader.numDocs();
	}

	/**
	 * Get the  next document from the index.
	 */
	public Document next() {
		Document doc = null;
		if(hasNext()){
			try {
				doc = indexReader.document(currentIndex);
				currentIndex++;
			}
			catch(IOException ioe) {
				ioe.printStackTrace();
			}
		}
		return doc;
	}

	public void remove() {
		// TODO Auto-generated method stub

	}

}

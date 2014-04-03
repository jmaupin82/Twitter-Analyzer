package org.db2project.EventDetection.Classifier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;

import org.db2project.EventDetection.Event;


public class ClutoParser {

	/**
	 * Once we have cluto output (we have clustered the
	 * data), we want to retrieve the defining features
	 * of each cluster, which will hopefully identify
	 * objects.
	 * 
	 * @param clutoOutput
	 * @return
	 * @throws IOException 
	 */
	public static Set<Event> parse(String clutoOutput) throws IOException {
		HashSet<Event> eventSet = new HashSet<Event>();

		// The number of clusters according to CLUTO.
		int clusterNumber;

		BufferedReader clutoOutputReader = 
				new BufferedReader(new StringReader(clutoOutput));	

		/*
		 * Read lines until we reach the one that starts with the word
		 * "Options".
		 */
		boolean foundOption = false;

		String line = "";			
		while ((line = clutoOutputReader.readLine())!= null
				&& !foundOption) {
			foundOption = line.split(" ")[0].equals("Options");
		}

		String[] lineAfterOption = clutoOutputReader.readLine().split(" ");
		try{
			clusterNumber = Integer.parseInt(lineAfterOption[lineAfterOption.length-1]);
		}catch (NumberFormatException e){
			System.err.println("error while reading the number of clusters");
			return null;
		}



		/*
		 * Read lines until we reach the one that starts with the word
		 * "Solution".
		 */
		boolean foundSolution = false;

		line = "";			
		while ((line = clutoOutputReader.readLine())!= null
				&& !foundSolution) {
			foundSolution = line.split(" ")[0].equals("Solution");
		}

		/*
		 * Consume 4 lines until we reach to the headers of the table below.
		 * 
		 */
		// The number of lines between the line with "Solution" and the line
		// with the headers.
		final int numberOfLinesToHeader = 4;
		for(int i=0; i < numberOfLinesToHeader; i++) {
			clutoOutputReader.readLine();
		}

		// The line with the headers.
		line = clutoOutputReader.readLine();

		// These are the features that were output by CLUTO.
		String[] featureHeaders = line.split("|")[1].trim().split(" ");

		// Read and discard the line with the dashes.
		clutoOutputReader.readLine();

		// Read the data from every cluster.
		for(int i = 0; i < clusterNumber; i++) {
			line = clutoOutputReader.readLine();
			// Divides the line into two parts
			String[] halfLine = line.split("|");
			// This string has the cid, size, Isim,..., Purty
			String[] leftPart = halfLine[0].split(" ");
			String[] rightPart = halfLine[1].split(" ");

			float[] leftPartFloats = new float[leftPart.length];
			for(int j =0; j < leftPartFloats.length; j++) {
				leftPartFloats[j] = Float.parseFloat(leftPart[j]);
			}

			ClutoClusterResult clusterResult = new ClutoClusterResult((int)leftPartFloats[0],
					(int) leftPartFloats[1], leftPartFloats[2], leftPartFloats[3],
					leftPartFloats[4], leftPartFloats[5], leftPartFloats[6],
					leftPartFloats[7]);
		}

		return eventSet;
	}

}

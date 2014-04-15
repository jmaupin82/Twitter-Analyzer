package org.db2project.EventDetection;

import java.util.HashSet;
import java.util.Set;

import org.db2project.EventDetection.Classifier.AdjacencyStructure;
import org.db2project.EventDetection.Classifier.ClutoHandler;
import org.db2project.EventDetection.Classifier.CoOccurrenceGraphConstruction;
import org.db2project.EventDetection.Classifier.CoOccurrenceListGraph;
import org.db2project.EventDetection.Classifier.CoOccurrenceMatrixGraph;
import org.db2project.EventDetection.Classifier.Edge;
import org.db2project.EventDetection.Classifier.GraphNode;
import org.db2project.EventDetection.Classifier.TopicalWordsSelection;

/**
 * New Event detection using twitter streams.
 * 
 */
public class EventDetectorApp {
	
	public static void main(String[] args) {
		System.out.println("Event Detector started!");

		try {
			// Crawler.oauth(args[0], args[1], args[2], args[3]);

			/*
			 * Step 1. Find the topical Words
			 */
			TopicalWordsSelection topicalWordSelector = new TopicalWordsSelection();
			Set<String> topicalWords = topicalWordSelector.selectTopicalWords();
			
			System.out.println("[INFO] Number of Topical Words Found: " + topicalWords.size());
			//System.exit(0);
			/*
			 * Step 2. Build the co-Occurrence Graph
			 */
			CoOccurrenceListGraph graph = CoOccurrenceGraphConstruction
					.buildCoOccurrenceGraph(topicalWords);
			
			//try writing the graph to a file
			
			// The Graph needs to be normalized in order to call CLUTO
			graph.normalize();
			graph.printToFile("graph.txt");
			System.out.println("successfully print the graph!");
			/*
			 * Step 3. Call the Clustering Algorithm
			 * 
			 */
			ClutoHandler cluto = new ClutoHandler(AdjacencyStructure.MATRIX, graph);

			// Test Graph
//			GraphNode<String> node1 = new GraphNode<String>("node1");
//			GraphNode<String> node2 = new GraphNode<String>("node2");
//			Edge<String> edge = new Edge<String>(node1, node2, (float)0.0);
//			HashSet<GraphNode<String>> nodes = new HashSet<GraphNode<String>>();
//			nodes.add(node1);
//			nodes.add(node2);
//			HashSet<Edge<String>> edges = new HashSet<Edge<String>>();
//			CoOccurrenceMatrixGraph gr = new CoOccurrenceMatrixGraph(nodes, edges, nodes.size());
//			ClutoHandler cluto = new ClutoHandler(AdjacencyStructure.MATRIX, graph);
			
			// This is the string that is output by cluto after clustering.
			String clutoOutput = cluto.runClusteringAlgorithm();
			System.out.println("output: "+clutoOutput);
			
			/*
			 * Step 4. Parse the results that were generated by cluto.
			 */
			//Set<Event> setOfEvents = ClutoParser.parse(clutoOutput);
			
			/*
			 * Step 5. Interpret results and events and store them in the
			 * database
			 * 
			 */
			// TODO: DBOracle.getInstance().saveEvents(setEvents);


		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

package org.db2project.EventDetection.Classifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


public class CoOccurrenceGraphConstruction {

	/**
	 * Given a set of topical words and a set of tweets, it build the associated
	 * co-occurrence graph.
	 * 
	 * @param topicalWords
	 * @param tweets
	 */
	public static CoOccurrenceListGraph buildCoOccurrenceGraph(
			Set<String> topicalWords) {

		HashSet<Edge<String>> edges = new HashSet<Edge<String>>();
		HashSet<GraphNode<String>> nodes = new HashSet<GraphNode<String>>();

		/*
		 * Used to check the progress how many pairs of topical words
		 * have been examined.
		 * 
		 */
		int countTopicalWordPairs = 0;
		
		/* Build a set of edges between topical words that coOccur */
		
		
		/* 
		 * Let's make a map that given the String of TopicalWord returns the 
		 * corresponding GraphNode<String>'s, one GraphNode per
		 * topical word. 
		 * */
		HashMap<String, GraphNode<String>> topicalWordNodes = 
				new HashMap<String, GraphNode<String>>();
		
		for(String topicalWord: topicalWords) {
			GraphNode<String> node = 
					new GraphNode<String>(topicalWord);
			topicalWordNodes.put(topicalWord, node);
		}
		
		/*
		 * We iterate over all possible of pairs.
		 */
		for(String topicalWordOne : topicalWords) {
			GraphNode<String> src = 
					topicalWordNodes.get(topicalWordOne);
			
			for(String topicalWordTwo : topicalWords) {
				if (topicalWordOne.compareTo(topicalWordTwo) < 0) {
					int strength = IndexManager
							.getInstance()
							.countCoOccurrences(topicalWordOne, topicalWordTwo);

					if(strength != 0) {

						GraphNode<String> dst = 
								topicalWordNodes.get(topicalWordTwo);

						// Add the edge to the set of edges of the graph.
						edges.add(new Edge<String>(
								src, 
								dst, 
								strength));
						// Add the edge to the set of edges of the graph.
						edges.add(new Edge<String>(
								dst, 
								src, 
								strength));

						// Add both nodes to the set of nodes of the graph.
						if(!nodes.contains(src))
							nodes.add(src);
						if(!nodes.contains(dst))
							nodes.add(dst);
					} // End if (strength != 0)
				} // End if (!wordOne.equals(wordTwo)
				countTopicalWordPairs++;
			} // End for (Topical Word wordTwo : topicalWords)
	
		}  // End for (Topical Word wordOne : topicalWords)

		CoOccurrenceListGraph graph = 
				new CoOccurrenceListGraph(nodes,edges, nodes.size());
		
		return graph;
	}
}

package org.db2project.EventDetection.Classifier;
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
		for(String topicalWordOne : topicalWords) {
			for(String topicalWordTwo : topicalWords) {
				if (!topicalWordOne.equals(topicalWordTwo)) {
					int strength = IndexManager
							.getInstance()
							.countCoOccurrences(topicalWordOne, topicalWordTwo);

					if(strength != 0) {
						GraphNode<String> src = 
								new GraphNode<String>(topicalWordOne);
						GraphNode<String> dst = 
								new GraphNode<String>(topicalWordTwo);

						// Add the edge to the set of edges of the graph.
						edges.add(new Edge<String>(
								src, 
								dst, 
								strength));

						// Add both nodes to the set of nodes of the graph.
						nodes.add(src);
						nodes.add(dst);
					} // End if (strength != 0)
				} // End if (!wordOne.equals(wordTwo)
				countTopicalWordPairs++;
			} // End for (Topical Word wordTwo : topicalWords)
			System.out.println("Number of Pairs examined: " + countTopicalWordPairs);
		}  // End for (Topical Word wordOne : topicalWords)

		CoOccurrenceListGraph graph = 
				new CoOccurrenceListGraph(nodes,edges, nodes.size());

		return graph;
	}
}

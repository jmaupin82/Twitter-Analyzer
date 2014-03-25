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
			Set<TopicalWord> topicalWords,
			Set<Tweet> tweets) {

		HashSet<Edge<TopicalWord>> edges = new HashSet<Edge<TopicalWord>>();
		HashSet<GraphNode<TopicalWord>> nodes = new HashSet<GraphNode<TopicalWord>>();

		/* Build a set of edges between topical words that coOccur */
		for(TopicalWord wordOne : topicalWords) {
			for(TopicalWord wordTwo : topicalWords) {
				if (!wordOne.equals(wordTwo)) {
					int strength = coOccurs(wordOne, wordTwo);

					if(strength != 0) {
						GraphNode<TopicalWord> src = 
								new GraphNode<TopicalWord>(wordOne);
						GraphNode<TopicalWord> dst = 
								new GraphNode<TopicalWord>(wordTwo);

						// Add the edge to the set of edges of the graph.
						edges.add(new Edge<TopicalWord>(
								src, 
								dst, 
								strength));

						// Add both nodes to the set of nodes of the graph.
						nodes.add(src);
						nodes.add(dst);
					} // End if (strength != 0)
				} // End if (!wordOne.equals(wordTwo)
			} // End for (Topical Word wordTwo : topicalWords)
		}  // End for (Topical Word wordOne : topicalWords)

		CoOccurrenceListGraph graph = 
				new CoOccurrenceListGraph(nodes,edges, nodes.size());

		return graph;
	}

	/**
	 * This method returns the number of tweets in which both
	 * words provided co-occur.
	 * @param wordOne
	 * @param wordTwo
	 * @return
	 */
	private static int coOccurs(TopicalWord wordOne, TopicalWord wordTwo) {

		// We copy the set tweetSetOne, because the retainAll
		// method modifies the set in-place.
		Set<Tweet> tweetSetOne = new HashSet<Tweet>();	
		Set<Tweet> tweetSetTwo = wordTwo.getTweetsWhereOccurs();
		tweetSetOne.addAll(wordOne.getTweetsWhereOccurs());

		// Find the intersection of the set of tweets where
		// wordOne and wordTwo occur.
		tweetSetOne.retainAll(tweetSetTwo);

		// The size of the intersection is the strength of the
		// co-occurrence.
		return tweetSetOne.size();
	}
}

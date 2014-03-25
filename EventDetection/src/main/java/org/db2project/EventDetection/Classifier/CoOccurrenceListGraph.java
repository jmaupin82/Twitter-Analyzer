package org.db2project.EventDetection.Classifier;

import java.util.Set;

public class CoOccurrenceListGraph extends AdjacencyListGraph<TopicalWord> {

	public CoOccurrenceListGraph(Set<GraphNode<TopicalWord>> nodes,
			Set<Edge<TopicalWord>> edges,
			int numNodes) {
		
		// First add all the nodes
		for(GraphNode<TopicalWord> node: nodes) {
			this.addNode(node);
		}
		
		// Then add all the edges
		for(Edge<TopicalWord> edge: edges) {
			this.addEdge(edge.getSource(), edge.getDest(), edge.getCost());
		}
	}

}

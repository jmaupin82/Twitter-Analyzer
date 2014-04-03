package org.db2project.EventDetection.Classifier;

import java.util.Set;

public class CoOccurrenceListGraph extends AdjacencyListGraph<String> {

	public CoOccurrenceListGraph(Set<GraphNode<String>> nodes,
			Set<Edge<String>> edges,
			int numNodes) {
		
		// First add all the nodes
		for(GraphNode<String> node: nodes) {
			this.addNode(node);
		}
		
		// Then add all the edges
		for(Edge<String> edge: edges) {
			this.addEdge(edge.getSource(), edge.getDest(), edge.getCost());
		}
	}

}

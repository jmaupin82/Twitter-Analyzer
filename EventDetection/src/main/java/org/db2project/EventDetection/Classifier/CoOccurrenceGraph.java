package org.db2project.EventDetection.Classifier;
import java.util.Set;


public class CoOccurrenceGraph extends AdjacencyMatrixGraph<TopicalWord> {

	public CoOccurrenceGraph(Set<GraphNode<TopicalWord>> nodes,
			Set<Edge<TopicalWord>> edges,
			int numNodes) {
		super(nodes, edges, numNodes);
	}
	
}

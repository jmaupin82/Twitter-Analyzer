package org.db2project.EventDetection.Classifier;
import java.util.Set;


public class CoOccurrenceMatrixGraph extends AdjacencyMatrixGraph<String> {

	public CoOccurrenceMatrixGraph(Set<GraphNode<String>> nodes,
			Set<Edge<String>> edges,
			int numNodes) {
		super(nodes, edges, numNodes);
	}

	public void addEdge(GraphNode<String> source,
			GraphNode<String> target, float cost) {
		// TODO Auto-generated method stub
		
	}
	
}

package org.db2project.EventDetection.Classifier;
import java.util.Set;


public class CoOccurrenceMatrixGraph extends AdjacencyMatrixGraph<TopicalWord> {

	public CoOccurrenceMatrixGraph(Set<GraphNode<TopicalWord>> nodes,
			Set<Edge<TopicalWord>> edges,
			int numNodes) {
		super(nodes, edges, numNodes);
	}

	public void addEdge(GraphNode<TopicalWord> source,
			GraphNode<TopicalWord> target, float cost) {
		// TODO Auto-generated method stub
		
	}
	
}

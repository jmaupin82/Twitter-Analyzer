package org.db2project.EventDetection.Classifier;
/**
 * This class models a weighted edge of a directed graph.
 * @author eleal
 *
 * @param <T>
 */
public class Edge<T> {
	private GraphNode<T> source;
	private GraphNode<T> dest;
	private float cost;
	
	public Edge(GraphNode<T> source, GraphNode<T> dest, float cost) {
		this.source = source;
		this.dest   = dest;
		this.cost   = cost;		
	}

	public GraphNode<T> getSource() {
		return source;
	}

	public void setSource(GraphNode<T> source) {
		this.source = source;
	}

	public GraphNode<T> getDest() {
		return dest;
	}

	public void setDest(GraphNode<T> dest) {
		this.dest = dest;
	}

	public float getCost() {
		return cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}
	
	
}

package org.db2project.EventDetection.Classifier;
import java.util.List;



public abstract interface Graph<T> {
	
	/** To add a node to the graph */
	public abstract void addNode(GraphNode<T> node);
	
	/** To add an edge to the graph */
	public abstract void addEdge(GraphNode<T> source, GraphNode<T> target, float cost);
	
	/**
	 * This method is used to ensure that the graph is properly
	 * built. This is specially useful for adjacency matrix graphs.
	 */
	public abstract void buildGraph();
	
	/** Given a node it returns a list of the edges that have it as
	 * a source.
	 * @param node
	 * @return
	 */
	public abstract List<Edge<T>> getChildren(GraphNode<T> node);
	
	/**
	 * Prints the graph to a file, following the format of the CLUTO
	 * toolkit.
	 * @param filename
	 */
	public abstract void printToFile(String filename);
}
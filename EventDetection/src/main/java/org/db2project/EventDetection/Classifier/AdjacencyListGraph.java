package org.db2project.EventDetection.Classifier;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * This class models an graph using adjacency lists.
 * 
 * @author eleal
 *
 * @param <T>
 */
public class AdjacencyListGraph<T> implements Graph<T> {
	
	// The dictionary of adjacency lists.
	private LinkedHashMap<GraphNode<T>, ArrayList<Edge<T>>> nodes;

	// The number of nodes in the graph.
	private int numNodes;

	public AdjacencyListGraph() {
		nodes = new LinkedHashMap<GraphNode<T>, ArrayList<Edge<T>>>();
	}

	/**
	 * Returns the Dictionary that maps nodes to their
	 * adjacency lists.
	 * 
	 * @return Dictionary that maps nodes and adjacency lists.
	 * 
	 */

	public LinkedHashMap<GraphNode<T>, ArrayList<Edge<T>>> getAllNodes() {
		return nodes;
	}

	/**
	 * This method takes a node and adds it to the graph.
	 * 
	 * @param node Is a node that does not belong to the graph.
	 * 
	 */

	public void addNode(GraphNode<T> node) {
		// Create an empty adjacency list and add it to the
		// dictionary of adjacency lists.
		ArrayList<Edge<T>> emptyList = new ArrayList<Edge<T>>();
		getAllNodes().put(node, emptyList);

		// Update the count of the number of nodes.
		numNodes++;
	}

	/**
	 * This method takes 2 nodes and adds an edge from starNode
	 * to endNode to the Graph.
	 * 
	 * @param source Is a node in the graph. 
	 * @param target The destination node. It is a node in the
	 *                graph.
	 * @param cost The cost of the edge.
	 * 
	 */

	public void addEdge(GraphNode<T> source, GraphNode<T> target, float cost) {
		ArrayList<Edge<T>> sourceNodeChildren = getAllNodes().get(source);

		Edge<T> edgeFromSToT = new Edge<T>(source, target, cost);

		// If the edge is not there, we add it.
		sourceNodeChildren.add(edgeFromSToT);;
	}

	/**
	 * 
	 * @param node node is a node that belongs to the graph.
	 * @return Returns the list of children of node.
	 * 
	 * Note: Returns null if the given node is not part of
	 *       the graph.
	 * 
	 */

	public ArrayList<Edge<T>> getChildren(GraphNode<T> node) {
		return getAllNodes().get(node);
	}

	public LinkedHashMap<GraphNode<T>, ArrayList<Edge<T>>> getNodes(){
		return nodes;
	}

	/**
	 * This function normalizes a set of adjacency lists, by dividing each
	 * entry in it by the number of nodes.
	 */
	public void normalize() {		
		for(GraphNode<T> src : getNodes().keySet()) {
			for(Edge<T> edge : getNodes().get(src)){
				edge.setCost(edge.getCost() / (float)numNodes);
			}
		}
	}

	/**
	 * This method is used to print a set of adjacency lists to a file
	 * following the format used by the CLUTO toolkit.
	 * 
	 * @param filename
	 */
	public void printToFile(String filename) {
		try {
			FileWriter fileWriter = new FileWriter(filename);
			BufferedWriter bw = new BufferedWriter(fileWriter);

			// The first line must contain the number of nodes.
			bw.write(numNodes + "\n");

			// Print the matrix row by row.
			for(GraphNode<T> src : getNodes().keySet()) {
				bw.write(src.getName() + "\n");
				for(Edge<T> edge : getNodes().get(src)){
					//bw.write(  + " ");
					bw.write('\t');
					bw.write( edge.getDest() + ":" + edge.getCost() + " ");
				}
				if(getNodes().get(src).size() != 0){
					bw.write("\n");
				}
				
			}

			// Close the file.
			bw.close();				
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	/**
	 * This method is void. For adjacency matrix graphs, there is
	 * no need to do anything.
	 */
	public void buildGraph() {
		return;
	}
}


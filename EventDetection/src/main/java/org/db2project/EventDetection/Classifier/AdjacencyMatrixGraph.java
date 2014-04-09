package org.db2project.EventDetection.Classifier;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

/**
 * This class models a directed graph implemented with an 
 * adjacency matrix.
 * 
 * @author eleal
 *
 * @param <T>
 */
public class AdjacencyMatrixGraph<T> implements Graph<T> {

	/**
	 * These two map Nodes to their numeric Ids.
	 */
	private Hashtable<GraphNode<T>,Integer> nodeToNumMapping;
	private Hashtable<Integer,GraphNode<T>> numToNodeMapping;
	
	/**
	 * The adjacency matrix itself.
	 */
	protected float[][] adjacencyMatrix;
	
	/** The size of the adjacency matrix */
	protected int numNodes;
	
	
	private final boolean INCREMENTAL_BUILD;
	
	/** This attribute is only used only in the process of building the
	 * graph. It is used to store the partial list of nodes.
	 */
	private ArrayList<GraphNode<T>> tempNodes;
	
	/** This attribute is only used only in the process of building the
	 * graph. It is used to store the partial set of edges.
	 */
	private HashSet<Edge<T>> tempEdges;
	
	
	
	public AdjacencyMatrixGraph() {
		numNodes  = 0;
		tempNodes = new ArrayList<GraphNode<T>>();
		tempEdges = new HashSet<Edge<T>>();
		
		// Since no nodes or edges were provided, we assume the graph
		// will be created incrementally.
		INCREMENTAL_BUILD = true;
	}
	
	public AdjacencyMatrixGraph(Set<GraphNode<T>> nodes,
								Set<Edge<T>> edges,
								int numNodes) {
		nodeToNumMapping = new Hashtable<GraphNode<T>, Integer>(numNodes);
		numToNodeMapping = new Hashtable<Integer,GraphNode<T>>(numNodes);
		adjacencyMatrix  = new float[numNodes][numNodes];
		this.numNodes = numNodes;
		
		// Assign Ids to all the nodes.
		int key = 0;
		
		for(GraphNode<T> node: nodes){
			nodeToNumMapping.put(node, key);
			numToNodeMapping.put(key, node);
		}
		
		// Fill out the entries in the adjacency matrix.
		for(Edge<T> edge: edges){
			int row    = nodeToNumMapping.get(edge.getSource());
			int col    = nodeToNumMapping.get(edge.getDest());
			float weight = edge.getCost();
			
			adjacencyMatrix[row][col] = weight;
		}
		
		// Since all the nodes and edges were provided, we assume the graph
		// will not be created incrementally.
		INCREMENTAL_BUILD = false;
	}
	
	public void addNode(GraphNode<T> node) {
		// Add the node to the temporary list of nodes.
		tempNodes.add(node);
		
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
		Edge<T> edgeFromSToT = new Edge<T>(source, target, cost);

		tempEdges.add(edgeFromSToT);
	}

	
	//@Override
	public List<Edge<T>> getChildren(GraphNode<T> node) {
		// Get the Id corresponding to node.
		int nodeId = nodeToNumMapping.get(node);
		
		// TODO Auto-generated method stub
		ArrayList<Edge<T>> children = new ArrayList<Edge<T>>();
		
		for(int col = 0; col < numNodes; col++) {
			if (adjacencyMatrix[nodeId][col] != 0) {
				GraphNode<T> src = node;
				GraphNode<T> dst = numToNodeMapping.get(col);
				Edge<T> edge = new Edge<T>(src, dst, adjacencyMatrix[nodeId][col]);
				children.add(edge);
			}
		}
		return children;
	}

	/**
	 * This function normalizes an adjacency matrix, by dividing each
	 * entry in the matrix by the number of nodes.
	 */
	public void normalize() {
		for(int row = 0; row < numNodes; row++){
			for(int col = 0; col < numNodes; col++){
				adjacencyMatrix[row][col] /= (float) numNodes;
			}
		}
	}
	
	/**
	 * This method is used to print an adjacency matrix to a file
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
			for(int row = 0; row < numNodes; row++){
				for(int col = 0; col < numNodes; col++){
					bw.write(adjacencyMatrix[row][col] + " ");
				}
				bw.write("\n");
			}
			
			bw.close();			
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}	
	
	/**
	 * This method is used to build 
	 */
	public void buildGraph() {
		if(INCREMENTAL_BUILD){
			nodeToNumMapping = new Hashtable<GraphNode<T>, Integer>(numNodes);
			numToNodeMapping = new Hashtable<Integer,GraphNode<T>>(numNodes);
			adjacencyMatrix  = new float[numNodes][numNodes];

			// Assign Ids to all the nodes.
			int key = 0;

			for(GraphNode<T> node: tempNodes){
				nodeToNumMapping.put(node, key);
				numToNodeMapping.put(key, node);
			}

			// Fill out the entries in the adjacency matrix.
			for(Edge<T> edge: tempEdges){
				int row    = nodeToNumMapping.get(edge.getSource());
				int col    = nodeToNumMapping.get(edge.getDest());
				float weight = edge.getCost();

				adjacencyMatrix[row][col] = weight;
			}

			tempEdges = null;
			tempNodes = null;
		}

	}
}

package org.db2project.EventDetection.Classifier;
/**
 * This class models the node of  graph.
 * @author eleal
 *
 * @param <T>
 */
public class GraphNode<T> {	
	private String name;
	private long id;
	T element;
	
	public GraphNode(T element) {
		this.element = element;
		this.name = element.toString();
	}
	
	public GraphNode(T element, String name) {
		this.element = element;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public long getID(){
		return this.id;
	}
	
	public void setID(long id){
		this.id = id;
	}
	
	public String toString(){
		return name;
	}
	
	public T getElement(){
		return element;
	}
	
}

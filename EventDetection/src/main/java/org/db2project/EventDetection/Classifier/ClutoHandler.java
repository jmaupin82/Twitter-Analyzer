package org.db2project.EventDetection.Classifier;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * This class implements a handler for the Hierarchical clustering algorithm
 * implementation that is used in the project called CLUTO.
 * 
 * @author eleal
 *
 */
public class ClutoHandler {
	
	/** This is the number of clusters that will be used. */
	private static final int NUMBER_OF_CLUSTERS = 10;
	/** The name of the file that contains the incidence matrix */
	private static final String MATRIX_FILE = "sports.mat";
	/** The name of the file that contains the adjacency list. */
	private static final String LIST_FILE = "sports.mat";
	/** The name of the file that contains the labels for the features */
	private static final String LABEL_FILE = "sports.clabel";
	
	/** The operating system name */
	private static final String OS_NAME = System.getProperty("os.name")
									 	   .split(" ")[0]
										   .replace(" ", "");
	
	/** The path to the CLUTO binaries. */
	private static final String BINARIES_PATH = "./resources/" + OS_NAME + "/";
	/** This path is for test purposes only */
	private static final String PATH= "./samples/";
	/** The actual command that will be passed to a shell to run CLUTO. */
	private String command;
	/** The coOccurrence graph that is passed to the CLUTO handler.*/
	private Graph<String> graph;
	
	private String structureFile;
	
	public <T>ClutoHandler(AdjacencyStructure adjStr, Graph<String> graph) {
		// We keep the graph in order to pass it to cluto.
		this.graph = graph;
		
		String classFileName = null;
//		String structureFile = null;
		
		/*
		 * Depending on the type of AdjacencyStructure provided as an
		 * argument to the constructor, we will work with either an
		 * Adjacency List Graph or an Adjacency Matrix Graph.
		 */
		switch (adjStr) {
		case MATRIX:
			classFileName = PATH + "sports.rclass";
			structureFile = PATH + MATRIX_FILE;
			break;
		case LIST:
			classFileName = PATH + "sports.rclass";
			structureFile = PATH + LIST_FILE;
			break;
		}
		
		/*
		 * This is the command through which we can run the CLUTO binary.
		 * We provide the file with the graph and other useful parameters.
		 * 
		 */
		command = new StringBuilder(BINARIES_PATH)
						.append("vcluster -rclassfile=")
						.append(classFileName).append(" -clabelfile=")
						.append(LABEL_FILE)
						.append(' ' + structureFile)
						.append(' ' + String.valueOf(NUMBER_OF_CLUSTERS)).toString();
	}
	
	/**
	 * This method runs the cluto in a shell.
	 * 
	 * @return
	 */
	public String runClusteringAlgorithm() {
		System.out.println("[INFO] Running Cluto with command:");
		System.out.println(command);
		StringBuffer output = new StringBuffer();
		
		Process p;
		try {
			graph.printToFile(structureFile);
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			BufferedReader reader = 
                            new BufferedReader(new InputStreamReader(p.getInputStream()));
 
            String line = "";			
			while ((line = reader.readLine())!= null) {
				output.append(line + "\n");
			}
 
		} catch (Exception e) {
			e.printStackTrace();
		}
 
		return output.toString();
	}

}

package org.db2project.EventDetection.bl.crawler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.db2project.EventDetection.EventDetectorApp;
import org.json.simple.JSONArray;

import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.BasicClient;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import com.twitter.hbc.twitter4j.parser.JSONObjectParser;

public class Crawler {
	public static void oauth(String consumerKey, String consumerSecret, String token, String secret) {
		// Create an appropriately sized blocking queue
	    BlockingQueue<String> queue = new LinkedBlockingQueue<String>(10000);

	    // Define our endpoint: By default, delimited=length is set (we need this for our processor)
	    // and stall warnings are on.
	    StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();
	    endpoint.trackTerms(Lists.newArrayList("nba", "#yolo"));
	    endpoint.stallWarnings(false);
	    

	    Authentication auth = new OAuth1(consumerKey, consumerSecret, token, secret);
	    //Authentication auth = new com.twitter.hbc.httpclient.auth.BasicAuth(username, password);

	    // Create a new BasicClient. By default gzip is enabled.
	    BasicClient client = new ClientBuilder()
	      .name("sampleExampleClient")
	      .hosts(Constants.STREAM_HOST)
	      .endpoint(endpoint)
	      .authentication(auth)
	      .processor(new StringDelimitedProcessor(queue))
	      .build();

	    // Establish a connection
	    client.connect();
	    JSONObjectParser parser = new JSONObjectParser();
	    
	    // TODO: Output to a file for now, need to move to write to a DB
	    
	    try {
	    	File file = new File("/Users/pmahoro/Documents/OU/database/Twitter-Analyzer/EventDetection/data/tweets.txt");
		    // if file doesnt exists, then create it
 			if (!file.exists()) {
 				file.createNewFile();
 			}
	  
 			FileWriter fw = new FileWriter(file.getAbsoluteFile());
 			BufferedWriter bw = new BufferedWriter(fw);
		    

		    // Do whatever needs to be done with messages
		    for (int msgRead = 0; msgRead < 10; msgRead++) {
		    	if (client.isDone()) {
		    		System.out.println("Client connection closed unexpectedly: " + client.getExitEvent().getMessage());
		    		break;
		    	}

		      	String msg;
		      	JSONArray jsonArray = new JSONArray();
				try {
					msg = queue.poll(5, TimeUnit.SECONDS);
					if (msg == null) {
						System.out.println("Did not receive a message in 5 seconds");
				      	} else {
				      	// TODO: we create a jsonObject that only contains fields we care about. 
				    	// Right now we're just dumping the whole thing.
				    	System.out.println(msg);
				    	bw.write(msg);
				      }
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		    
		    bw.close();
	    }
	    catch (IOException e){
	    	e.printStackTrace();
	    }

	    client.stop();

	    // Print some stats
	    System.out.printf("The client read %d messages!\n", client.getStatsTracker().getNumMessages());
	    
	}

}

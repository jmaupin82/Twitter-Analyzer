package org.db2project.EventDetection.bl.crawler;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;




import org.db2project.EventDetection.Classifier.Tweet;

import twitter4j.internal.org.json.JSONArray;
import twitter4j.internal.org.json.JSONException;
import twitter4j.internal.org.json.JSONObject;

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

	
	private static DatabaseConnection db = new DatabaseConnection();


	public static void oauth(String consumerKey, String consumerSecret, String token, String secret) {
		// Create an appropriately sized blocking queue
		BlockingQueue<String> queue = new LinkedBlockingQueue<String>(10000);

		// Define our endpoint: By default, delimited=length is set (we need this for our processor)
		// and stall warnings are on.
		StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();
		endpoint.trackTerms(Lists.newArrayList("storm", "precipitation", "tornado", "blizzard", "weather"));
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

		// Do whatever needs to be done with messages
		for (int msgRead = 0; msgRead < 200000; msgRead++) {
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
					//System.out.println(msg);
					//build a tweet from the message
					buildTweetFromMessage(msg);

				}
			} catch (Exception e) {
				//TODO: we need to be able to restart the connection if it happens to fail. 
				e.printStackTrace();
			}
		}




		client.stop();

		// Print some stats
		System.out.printf("The client read %d messages!\n", client.getStatsTracker().getNumMessages());

	}

	/**
	 * 
	 * @param msg 			Message returned from the twitter stream. Includes all of the meta-data
	 * @throws JSONException Could throw a JSON exception if the message is null.
	 */
	public static void buildTweetFromMessage(String msg) throws JSONException{
		//use a JSON object to parse the tweet
		JSONObject obj = new JSONObject(msg);
		JSONObject author = new JSONObject(obj.getString("user"));
		String userName = author.getString("name");
		String text = obj.getString("text");
		text = stripCommas(text);
		String date = obj.getString("created_at");
		//get the time and date out of the big date string
		String[] split = date.split(" ");
		//get the time position
		String[] timeSplit = split[3].split(":");
		String time = timeSplit[0] + ":" + timeSplit[1];
		date = split[1] + " " + split[2];
		
//		//Tweet tMessage = new Tweet(userName, date, time, text);
//		//System.out.println(tMessage);
//		if(db.saveTweet(tMessage)){
//			System.out.println("message successfully saved!");
//		}
//		else{
//			System.out.println("the message was not saved :(");
//		}
//		
	}
	
	/**
	 * Primitive parsing function that will strip out commas from the tweets
	 * 
	 * @param input		Input String that does or doesn't contain commas
	 * @return			String free of commas
	 */
	public static String stripCommas(String input){
		
		String[] temp = input.split(",");
		String result = "";
		for(int i = 0; i < temp.length; i++){
			result+= temp[i];
		}
		
		return result;
	}

}

package com.cs5513;

import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.BasicClient;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import com.twitter.hbc.twitter4j.parser.JSONObjectParser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.Lists;

public class Crawler {
    /**
     * twitterAuth holds the authentication keys.
     */
	private static TwitterAuth twitterAuth;

    public Crawler(Map<String, String> map){
        twitterAuth = new TwitterAuth(map);
    }

    public Crawler(String apiKey, String apiSecret, String token, String tokenSecret){

    }

    public  void doCrawl(int cap){
        // Create an appropriately sized blocking queue
        BlockingQueue<String> queue = new LinkedBlockingQueue<String>(10000);

        // Define our endpoint: By default, delimited=length is set (we need this for our processor)
        // and stall warnings are on.
        StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();
        endpoint.trackTerms(Lists.newArrayList("weather", "tornado"));
        endpoint.stallWarnings(false);

        System.out.print(twitterAuth.ApiKey() + twitterAuth.ApiSecretKey() + twitterAuth.AccessToken() + twitterAuth.AccessTokenSecret());
        Authentication auth = new OAuth1(
                twitterAuth.ApiKey(),
                twitterAuth.ApiSecretKey(),
                twitterAuth.AccessToken(),
                twitterAuth.AccessTokenSecret()
        );

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
                boolean l = file.createNewFile();
                if (!l){
                    System.out.println("Could not open or create the output file.");
                }
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);


            // Do whatever needs to be done with messages
            for (int msgRead = 0; msgRead < cap; msgRead++) {
                if (client.isDone()) {
                    System.out.println("Client connection closed unexpectedly: " + client.getExitEvent().getMessage());
                    break;
                }

                String msg;
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

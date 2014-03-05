package com.cs5513;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {

        Properties config = new Properties();
        InputStream input = null;

        try {
            // TODO: Clean this up, once we add the db.
            input = new FileInputStream("/Users/pmahoro/Documents/OU/database/Twitter-Analyzer/TwitterAnalyzer/TwitterCrawler/src/com/config.properties");

            //load properties file
            config.load(input);

            Map<String, String> keyMap = new HashMap<String, String>();
            keyMap.put("apiKey", config.getProperty("apikey"));
            keyMap.put("apiSecretKey", config.getProperty("apisecretkey"));
            keyMap.put("accessToken", config.getProperty("accesstoken"));
            keyMap.put("accessTokenSecret", config.getProperty("accesstokensecret"));

            Crawler twitterCrawler = new Crawler(keyMap);
            twitterCrawler.doCrawl(10);

        } catch (IOException ex){
            ex.printStackTrace();
        }
        finally {
            if (input != null){
                try {
                    input.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
}

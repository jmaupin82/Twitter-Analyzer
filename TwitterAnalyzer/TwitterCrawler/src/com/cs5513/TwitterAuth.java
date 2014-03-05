package com.cs5513;

import java.util.Map;

public class TwitterAuth {
	/**
	 * apiKey is provided by Twitter
	 */
	private String apiKey;
	
	/**
	 * apiSecretKey allows app access to twitter stream
	 */
	private String apiSecretKey;
	
	/**
	 * accessToken for Twitter user OAuth
	 */
	private String accessToken;
	
	/**
	 * accessTokenSecret for Twitter user OAuth
	 */
	private String accessTokenSecret;
	
	/**
	 * Create instance of twitter Authentication, given keys.
	 */
	public TwitterAuth(String appkey, String appsecretkey, String token, String tokensecret){
		apiKey = appkey;
		apiSecretKey = appsecretkey;
		accessToken = token;
		accessTokenSecret = tokensecret;
	}
	
	public TwitterAuth(Map<String, String> twitterKeyMap){
        apiKey = twitterKeyMap.get("apiKey");
        apiSecretKey = twitterKeyMap.get("apiSecretKey");
        accessToken = twitterKeyMap.get("accessToken");
        accessTokenSecret = twitterKeyMap.get("accessTokenSecret");
	}
	
	
	public final String ApiKey(){
		return apiKey;
	}
	
	public final String ApiSecretKey(){
		return apiSecretKey;
	}
	
	public final String AccessToken(){
		return accessToken;
	}
	
	public final String AccessTokenSecret(){
		return accessTokenSecret;
	}
	
}

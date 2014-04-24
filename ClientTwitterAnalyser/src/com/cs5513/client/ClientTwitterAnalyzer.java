package com.cs5513.client;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.cs5513.client.items.EventItem;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;


public class ClientTwitterAnalyzer {

	public static final String BASE_URI = "http://localhost:8080/TwitterAnalyzer";
	public static final String PATH_EVENTS = "/rest/events";
	public static final String DATE_FORMAT = "MM-dd-yyyy";
	
	
	public static void main(String[] args) {
		
		ClientConfig config;
		Client client;
		WebResource service;
		String json = null;
		
		config = new DefaultClientConfig();
		client = Client.create(config);
		service = client.resource(getRootURI());
		
		
		if (args.length == 0) //no parameters
		{
			json = getAllEvents(service);
		}
		else if (args.length == 1)
		{
			String date = args[0];
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
			try {
				sdf.parse(date);
				json = getAllEventsByDate(date, service);
			} catch (ParseException e) {
				System.err.println("Error - Invalid date");
				System.err.println("Usage: ClientTwitterAnalyzer [MM-dd-yyyy]");
			}

		}
		else
		{
			System.err.println("Error - Useless arguments");
			System.err.println("Usage: ClientTwitterAnalyzer [MM-dd-yyyy]");
			return;
		}
		
		if (json != null)
		{
			displayEvents(json);
		}
	}
	
	private static URI getRootURI()
	{
		return UriBuilder.fromUri(BASE_URI).build();
	}
	
	private static String getAllEvents(WebResource service)
	{
		return service.path(PATH_EVENTS).accept(MediaType.APPLICATION_JSON).get(String.class);
	}
	
	private static String getAllEventsByDate(String date, WebResource service)
	{
		return service.path(PATH_EVENTS).path(date).accept(MediaType.APPLICATION_JSON).get(String.class);
	}
	
	private static void displayEvents(String json)
	{
		ArrayList<EventItem> events = null;
		
		if (!json.equals("null"))
		{
			try {
				events = EventParser.parse(new JSONObject(json));
				
				for(EventItem e : events)
				{
					System.out.println(e);
				}
				
			} catch (JSONException e) {
				System.err.println("JSON Parsing error");
				e.printStackTrace();
			}
		}
		else
		{
			System.out.println("no result");
		}
	}
	

}

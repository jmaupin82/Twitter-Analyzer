package com.cs5513.presentationlayer;

import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import com.cs5513.businesslayer.BusinessManager;
import com.cs5513.entities.Event;


@Path("/events")
public class EventsResource {

	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Event> getAllEvents()
	{
		List<Event> events = new ArrayList<Event>();
		events.addAll(BusinessManager.getInstance().getAllEvents());
		return events;
	}
	
	@GET
	@Path("{date}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Event> getEventByDate(@PathParam("date") String date)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		List<Event> events = new ArrayList<Event>();
		try {
			events.addAll(BusinessManager.getInstance().getEventsByDate(sdf.parse(date)));
		} catch (ParseException e) {
			System.err.println("error while parsing date");
			e.printStackTrace();
		}
		return events;
	}
}

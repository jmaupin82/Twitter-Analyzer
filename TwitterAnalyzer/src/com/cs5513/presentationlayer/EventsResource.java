package com.cs5513.presentationlayer;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
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
	public List<Event> getEvents()
	{
		List<Event> events = new ArrayList<Event>();
		events.addAll(BusinessManager.getInstance().getAllEvents());
		return events;
	}
}

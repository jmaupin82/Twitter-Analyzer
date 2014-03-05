package com.cs5513.presentationlayer;

import java.awt.PageAttributes.MediaType;
import java.util.ArrayList;
import java.util.List;

import org.omg.CORBA.Request;

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

package com.sermon.mynote.web.controller;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sermon.mynote.domain.Event;
import com.sermon.mynote.domain.EventDetails;
import com.sermon.mynote.domain.EventInput;
import com.sermon.mynote.domain.EventType;
import com.sermon.mynote.domain.StatusResponse;
import com.sermon.mynote.service.EventsService;

@RequestMapping("/event")
@Controller
public class EventControlller {

	final Logger logger = LoggerFactory.getLogger(EventControlller.class);

	@Autowired
	private EventsService eventService;

	@PersistenceContext
	private EntityManager em;

	@RequestMapping(value = "/getEventTypes", method = RequestMethod.GET,  produces = "application/json")
	@ResponseBody
	public List<EventType> getEventTypes() {
		logger.info("Listing contacts");

		List<EventType> vwOrgInfo = eventService.getEventTypes();

		return vwOrgInfo;
	}
	
	
	/*@RequestMapping(value = "/getEventLists", method = RequestMethod.POST, produces = "application/json")
	@RequestBody
	public List<EventsList> getEventsList(){
		
		return null;
	}*/

	@RequestMapping(value = "/createEvent", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Event createEvent(@RequestBody Event eventInput) {

		
		return eventService.createEvent(eventInput);
	}
	
	
	@RequestMapping(value = "/getEventDetails", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public EventDetails getEventDetails(@RequestBody EventInput eventInput) {
        System.out.println("GET EVENT DETAILS");
		
		return eventService.getEventDetails(eventInput.getEventId());
	}
	
	@RequestMapping(value = "/getEvents", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Event> getEventsList() {
        System.out.println("GET EVENT DETAILS");
		
		return eventService.getEventsList();
	}
	
	

	
	
	/* update */
	@RequestMapping(value = "/updateEvent", method = RequestMethod.POST, produces = "application/json", consumes="application/json")
	@ResponseBody
	public StatusResponse updateNote(@RequestBody Event event) {

		Event eventTemp = new Event();
		/*
		 * Date date = new Date(); note.setEventDate(new
		 * Timestamp(date.getTime())); note.setEventTime(new
		 * Timestamp(date.getTime()));
		 */

		eventTemp = eventService.findById(event.getEventId());
		
		eventTemp.setAddress1(event.getAddress1());
		eventTemp.setAddress2(event.getAddress2());
		eventTemp.setCityId(event.getCityId());
		eventTemp.setContactEmail(event.getContactEmail());
		eventTemp.setContactFB(event.getContactFB());
		eventTemp.setContactPhone(event.getContactPhone());
		eventTemp.setContactEmail(event.getContactEmail());
		eventTemp.setContactWebSite(event.getContactWebSite());
		eventTemp.setCountryID(event.getCountryID());
		eventTemp.setCreateDate(event.getCreateDate());
		eventTemp.setDescription(event.getDescription());
		eventTemp.setEndTime(event.getEndTime());
		eventTemp.setEventLocation(event.getEventLocation());
		eventTemp.setEventTypeId(event.getEventTypeId());
		eventTemp.setFromDate(event.getFromDate());
		eventTemp.setHost(event.getHost());
		eventTemp.setLocationLat(event.getLocationLat());
		eventTemp.setLocationLong(event.getLocationLong());
		eventTemp.setModifiedDate(event.getModifiedDate());
		eventTemp.setOrganizationID(event.getOrganizationID());
		eventTemp.setOtherInfo(event.getOtherInfo());
		eventTemp.setStartTime(event.getStartTime());
		eventTemp.setStateid(event.getStateid());
		eventTemp.setTitle(event.getTitle());
		eventTemp.setToDate(event.getToDate());
		eventTemp.setZipCode(event.getZipCode());

	
		Event eventResponse = eventService.save(eventTemp);

		StatusResponse response = new StatusResponse();

		if (eventResponse != null)
			response.setStatus(true);
		else
			response.setStatus(false);

		return response;

	}

	
	@RequestMapping(value = "/deleteEvent", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public StatusResponse deleteEvent(@RequestBody EventInput eventInput) {
		
		return eventService.deleteEvent(eventInput.getEventId());
	}
	
	
	
	
}

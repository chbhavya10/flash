package com.sermon.mynote.service;

import java.io.InputStream;
import java.util.List;


import com.amazonaws.services.s3.transfer.Upload;
import com.sermon.mynote.domain.Event;
import com.sermon.mynote.domain.EventDetails;
import com.sermon.mynote.domain.EventType;
import com.sermon.mynote.domain.StatusResponse;

public interface EventsService {
	
	List<EventType> getEventTypes();
	
	Event createEvent(Event eventData);
	
	EventDetails getEventDetails(int eventId);
	
	List<Event> getEventsList();
	
	int saveImage(int eventId, String imgName);
	
	Event findById(Integer id);

	Event save(Event event);
	
	String getEventImage(int noteId);
		
	Upload uploadEventImage(InputStream inputStream, String imgName, String imgToDelete, int noteId);

	StatusResponse deleteEvent(int eventId);
	
	InputStream getEventImageAsStream(int eventId);
	




}

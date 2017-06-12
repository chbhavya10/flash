package com.sermon.mynote.service.jpa;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.amazonaws.util.IOUtils;
import com.google.common.collect.Lists;
import com.sermon.mynote.domain.Event;
import com.sermon.mynote.domain.EventDetails;
import com.sermon.mynote.domain.EventType;
import com.sermon.mynote.domain.EventsList;
import com.sermon.mynote.domain.Organization;
import com.sermon.mynote.domain.StatusResponse;
import com.sermon.mynote.repository.EventRepository;
import com.sermon.mynote.repository.EventTypeRepository;
import com.sermon.mynote.service.EventsService;
import com.sermon.util.AppConstants;



@Service("eventService")
@Repository
@Transactional
public class EventsServiceImpl implements EventsService {

	final Logger logger = LoggerFactory.getLogger(EventsServiceImpl.class);

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private EventTypeRepository eventTypeRepository;

	@Value("${s3.aws.access.key.id}")
	private String awsAccessKeyId;

	@Value("${s3.aws.access.secret.key}")
	private String awsAccessSecretKey;

	@Value("${s3.aws.bucket.name}")
	private String s3BucketName;

	public Event findById(Integer id) {
		return eventRepository.findOne(id.intValue());
	}

	public Event save(Event event) {

		/*
		 * Date date = new Date(); note.setEventDate(new
		 * Timestamp(date.getTime())); note.setEventTime(new
		 * Timestamp(date.getTime()));
		 */

		return eventRepository.save(event);
	}

	@Override
	public List<EventType> getEventTypes() {

		@SuppressWarnings("unchecked")
		TypedQuery<EventType> query = (TypedQuery<EventType>) em.createNativeQuery("SELECT * FROM `EventType`",
				EventType.class);

		List<EventType> results = (List<EventType>) query.getResultList();

		return results;

	}

	@Override
	public Event createEvent(Event eventData) {

		return eventRepository.save(eventData);
	}

	@Override
	public EventDetails getEventDetails(int eventId) {

		EventDetails eventDetailsRes = new EventDetails();

		String QUERY_GET_EVENT = "SELECT * FROM Event WHERE eventId =" + eventId;

		TypedQuery<Event> eventDetailsObj = (TypedQuery<Event>) em.createNativeQuery(QUERY_GET_EVENT, Event.class);

		List<Event> eventDetails = (List<Event>) eventDetailsObj.getResultList();
		if (eventDetails.size() == 0) {
			return null;
		} else {

			String QUERY_GET_ORGANIZATION_NAME = "SELECT * FROM `organization` WHERE `OrganizationId` = "
					+ eventDetails.get(0).getOrganizationID();
			TypedQuery<Organization> organizationQuery = (TypedQuery<Organization>) em
					.createNativeQuery(QUERY_GET_ORGANIZATION_NAME, Organization.class);

			Organization organizationName = (Organization) organizationQuery.getSingleResult();
			System.out.println(" Organization Name " + organizationName.getOrganizationName());

			eventDetailsRes.setAddress1(eventDetails.get(0).getAddress1());
			eventDetailsRes.setAddress2(eventDetails.get(0).getAddress2());
			eventDetailsRes.setCityId(eventDetails.get(0).getCityId());
			eventDetailsRes.setStartTime(eventDetails.get(0).getStartTime());
			eventDetailsRes.setEndTime(eventDetails.get(0).getEndTime());
			eventDetailsRes.setContactEmail(eventDetails.get(0).getContactEmail());
			eventDetailsRes.setContactFB(eventDetails.get(0).getContactFB());
			eventDetailsRes.setContactPhone(eventDetails.get(0).getContactPhone());
			eventDetailsRes.setContactWebSite(eventDetails.get(0).getContactWebSite());
			eventDetailsRes.setCountryID(eventDetails.get(0).getCountryID());
			eventDetailsRes.setCreateDate(eventDetails.get(0).getCreateDate());
			eventDetailsRes.setDescription(eventDetails.get(0).getDescription());
			eventDetailsRes.setEventId(eventDetails.get(0).getEventId());
			eventDetailsRes.setEventLocation(eventDetails.get(0).getEventLocation());
			eventDetailsRes.setEventTypeId(eventDetails.get(0).getEventTypeId());
			eventDetailsRes.setFromDate(eventDetails.get(0).getFromDate());
			eventDetailsRes.setHost(eventDetails.get(0).getHost());
			eventDetailsRes.setLocationLat(eventDetails.get(0).getLocationLat());
			eventDetailsRes.setLocationLong(eventDetails.get(0).getLocationLong());
			eventDetailsRes.setModifiedDate(eventDetails.get(0).getModifiedDate());
			eventDetailsRes.setOrganizationID(eventDetails.get(0).getOrganizationID());
			eventDetailsRes.setOrganizationName(organizationName.getOrganizationName());
			eventDetailsRes.setOtherInfo(eventDetails.get(0).getOtherInfo());
			eventDetailsRes.setStateid(eventDetails.get(0).getStateid());
			eventDetailsRes.setTitle(eventDetails.get(0).getTitle());
			eventDetailsRes.setToDate(eventDetails.get(0).getToDate());
			eventDetailsRes.setZipCode(eventDetails.get(0).getZipCode());

			return eventDetailsRes;
		}
	}

	@Override
	public List<Event> getEventsList() {

		List<EventsList> eventLists = new ArrayList<EventsList>();
		TypedQuery<Event> query = (TypedQuery<Event>) em
				.createNativeQuery(" SELECT * FROM Event WHERE  ToDate > NOW() OR ToDate = NOW()", Event.class);

		List<Event> results = (List<Event>) query.getResultList();

		return results;
	}

	
	@Override
	public int saveImage(int eventId, String imgName) {

		try {
			Query query = em.createNativeQuery("update Event set EventImage=" + imgName + " where EventId=" + eventId);

			int result = query.executeUpdate();
			return result;

		} catch (NoResultException e) {

		}
		return 0;
	}

	
	@Override
	public String getEventImage(int eventId) {
		String eventImage = null;

		try {
			Query query = em.createNativeQuery("select noteImage returnvalue from note where NoteId=:NoteId")
					.setParameter("NoteId", eventId);

			if (query.getSingleResult() != null) {
				eventImage = (String) query.getSingleResult();
			}
		} catch (NoResultException e) {

		}
		return eventImage;
	}

	@Override
	public StatusResponse deleteEvent(int eventId) {
		
		StatusResponse statusResponse = new StatusResponse();
		
		Query query = em.createQuery("DELETE FROM Event WHERE eventId = "+eventId);
	    int deleted = query.executeUpdate();
	    
	    if(deleted > 0){
	      statusResponse.setStatus(true);
	      statusResponse.setMessage("Event Deleted Sucessfully");
	    }else{
	    	statusResponse.setStatus(false);
		      statusResponse.setMessage("Event Deletion is Failure");
	    }
	    
	    return statusResponse;
	}

}

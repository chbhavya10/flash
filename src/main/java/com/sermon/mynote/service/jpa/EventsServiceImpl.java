package com.sermon.mynote.service.jpa;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
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
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.amazonaws.util.IOUtils;
import com.google.common.collect.Lists;
import com.sermon.mynote.domain.Event;
import com.sermon.mynote.domain.EventDetails;
import com.sermon.mynote.domain.UserFavorateEvents;
import com.sermon.mynote.domain.EventType;
import com.sermon.mynote.domain.EventsList;
import com.sermon.mynote.domain.Organization;
import com.sermon.mynote.domain.PublishSchedule;
import com.sermon.mynote.domain.StatusResponse;
import com.sermon.mynote.repository.EventFavoriteRepository;
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
	private EventFavoriteRepository eventFavorateRepository;

	@Autowired
	private EventTypeRepository eventTypeRepository;

	
	@Value("${event.image.bucket.path}")
	private String eventImageBucketPath;
	

	@Value("${s3.aws.access.key.id}")
	private String awsAccessKeyId;

	@Value("${s3.aws.access.secret.key}")
	private String awsAccessSecretKey;

	@Value("${s3.aws.bucket.name}")
	private String s3BucketName;

	private CrudRepository<Event, Integer> eventFavoriteRepository;

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
			Event tempEvent = eventDetails.get(0);

			String QUERY_GET_ORGANIZATION_NAME = "SELECT * FROM `organization` WHERE `OrganizationId` = "
					+ tempEvent.getOrganizationID();
			TypedQuery<Organization> organizationQuery = (TypedQuery<Organization>) em
					.createNativeQuery(QUERY_GET_ORGANIZATION_NAME, Organization.class);

			Organization organizationName = (Organization) organizationQuery.getSingleResult();
			System.out.println(" Organization Name " + organizationName.getOrganizationName());

			/*String bucketName = s3BucketName + AppConstants.SLASH + eventImageBucketPath;
			String noteImgPath = null;
			String eventImage = tempEvent.getEventImage();
			if (eventImage != null) {
				String s3Obj = tempEvent.getEventId() + AppConstants.SLASH + eventImage;
				noteImgPath = generatePreSignedURL(bucketName, s3Obj);
				eventDetailsRes.setEventImage(noteImgPath);
			} else {
				
				 * String s3Obj = AppConstants.DEFAULT_ID + AppConstants.SLASH +
				 * AppConstants.DEFAULT_NOTE_IMAGE; noteImgPath =
				 * generatePreSignedURL(bucketName, s3Obj);
				 * eventDetailsRes.setEventImage(noteImgPath);
				 
				eventDetailsRes.setEventImage(eventDetails.get(0).getEventImage());

			}
			*/
			String bucketName = s3BucketName + AppConstants.SLASH + eventImageBucketPath;
			String eventImagePath = null;
			String eventImage = tempEvent.getEventImage();
			if (eventImage != null) {
				String s3Obj = tempEvent.getEventId() + AppConstants.SLASH + eventImage;
				eventImagePath = generatePreSignedURL(bucketName, s3Obj);
				eventDetailsRes.setEventImage(eventImagePath);
			} else {
				  String s3Obj = AppConstants.DEFAULT_ID + AppConstants.SLASH +
				  AppConstants.DEFAULT_NOTE_IMAGE; eventImagePath =
				  generatePreSignedURL(bucketName, s3Obj);
				  eventDetailsRes.setEventImage(eventImagePath);
			}

			eventDetailsRes.setAddress1(tempEvent.getAddress1());
			eventDetailsRes.setAddress2(tempEvent.getAddress2());
			eventDetailsRes.setCityId(tempEvent.getCityId());
			eventDetailsRes.setStartTime(tempEvent.getStartTime());
			eventDetailsRes.setEndTime(tempEvent.getEndTime());
			eventDetailsRes.setContactEmail(tempEvent.getContactEmail());
			eventDetailsRes.setContactFB(tempEvent.getContactFB());
			eventDetailsRes.setContactPhone(tempEvent.getContactPhone());
			eventDetailsRes.setContactWebSite(tempEvent.getContactWebSite());
			eventDetailsRes.setCountryID(tempEvent.getCountryID());
			eventDetailsRes.setCreateDate(tempEvent.getCreateDate());
			eventDetailsRes.setDescription(tempEvent.getDescription());
			eventDetailsRes.setEventId(tempEvent.getEventId());
			eventDetailsRes.setEventLocation(tempEvent.getEventLocation());
			eventDetailsRes.setEventTypeId(tempEvent.getEventTypeId());
			eventDetailsRes.setFromDate(tempEvent.getFromDate());
			eventDetailsRes.setHost(tempEvent.getHost());
			eventDetailsRes.setLocationLat(tempEvent.getLocationLat());
			eventDetailsRes.setLocationLong(tempEvent.getLocationLong());
			eventDetailsRes.setModifiedDate(tempEvent.getModifiedDate());
			eventDetailsRes.setOrganizationID(tempEvent.getOrganizationID());
			eventDetailsRes.setOrganizationName(organizationName.getOrganizationName());
			eventDetailsRes.setOtherInfo(tempEvent.getOtherInfo());
			eventDetailsRes.setStateid(tempEvent.getStateid());
			eventDetailsRes.setTitle(tempEvent.getTitle());
			eventDetailsRes.setToDate(tempEvent.getToDate());
			eventDetailsRes.setZipCode(tempEvent.getZipCode());

			return eventDetailsRes;
		}
	}

	public List<Event> getEventsByOrg(int organizationId) {

		TypedQuery<Event> query = (TypedQuery<Event>) em
				.createNativeQuery("SELECT * FROM `Event` WHERE `OrganizationID`=" + organizationId+" AND ToDate > NOW() OR ToDate = NOW()", Event.class);

		List<Event> results = (List<Event>) query.getResultList();
		
		List<Event> eventLists = new ArrayList<Event>();

		for(int i = 0;i<results.size(); i ++){
			
			Event eventTemp = results.get(i);
			Event event = new Event();
			event.setStartTime(eventTemp.getStartTime());
			event.setEndTime(eventTemp.getEndTime());
			event.setHost(eventTemp.getHost());
			event.setDescription(eventTemp.getDescription());
			event.setOrganizationID(eventTemp.getOrganizationID());
			event.setContactWebSite(eventTemp.getContactWebSite());
			event.setAddress2(eventTemp.getAddress2());
			event.setAddress1(eventTemp.getAddress1());
			event.setCityId(eventTemp.getCityId());
			event.setContactEmail(eventTemp.getContactEmail());
			event.setFromDate(eventTemp.getFromDate());
			event.setCountryID(eventTemp.getCountryID());
			event.setContactFB(eventTemp.getContactFB());
			event.setEventLocation(eventTemp.getEventLocation());
			event.setModifiedDate(eventTemp.getModifiedDate());
			event.setEventId(eventTemp.getEventId());
			event.setOtherInfo(eventTemp.getOtherInfo());
			event.setStateid(eventTemp.getStateid());
			event.setCreateDate(eventTemp.getCreateDate());
			event.setEventTypeId(eventTemp.getEventTypeId());
			event.setContactPhone(eventTemp.getContactPhone());
			event.setLocationLat(eventTemp.getLocationLat());
			event.setLocationLong(eventTemp.getLocationLong());
			event.setToDate(eventTemp.getToDate());
			event.setZipCode(eventTemp.getZipCode());
			event.setTitle(eventTemp.getTitle());
			
			
			String bucketName = s3BucketName + AppConstants.SLASH + eventImageBucketPath;
			String eventImagePath = null;
			String eventImage = eventTemp.getEventImage();
			if (eventImage != null) {
				String s3Obj = eventTemp.getEventId() + AppConstants.SLASH + eventImage;
				eventImagePath = generatePreSignedURL(bucketName, s3Obj);
				event.setEventImage(eventImagePath);
			} else {
				  String s3Obj = AppConstants.DEFAULT_ID + AppConstants.SLASH +
				  AppConstants.DEFAULT_NOTE_IMAGE; eventImagePath =
				  generatePreSignedURL(bucketName, s3Obj);
				  event.setEventImage(eventImagePath);
			}
			
		  System.out.println(event.getEventImage());
			
			eventLists.add(event);
		}

		return eventLists;
	}

	public String generatePreSignedURL(String bucketName, String objectKey) {

		java.util.Date expiration = new java.util.Date();
		long milliSeconds = expiration.getTime();
		milliSeconds += AppConstants.EXPIRY_SECONDS;
		expiration.setTime(milliSeconds);

		GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName,
				objectKey);
		generatePresignedUrlRequest.setMethod(HttpMethod.GET);
		generatePresignedUrlRequest.setExpiration(expiration);
		AmazonS3 ams3 = getAmazonS3Client();
		ams3.setEndpoint(AppConstants.AMAZON_LINK);

		URL url = ams3.generatePresignedUrl(generatePresignedUrlRequest);

		return url.toString();

	}

	@Override
	public List<EventDetails> getEventsList(int userId) {

		// List<EventsList> eventLists = new ArrayList<EventsList>();
		TypedQuery<Event> query = (TypedQuery<Event>) em
				.createNativeQuery(" SELECT * FROM Event WHERE  ToDate > NOW() OR ToDate = NOW()", Event.class);

		List<Event> eventDetails = (List<Event>) query.getResultList();
		
		List<EventDetails> eventLists = new ArrayList<EventDetails>();

		for(int i = 0;i<eventDetails.size(); i ++){
			
			Event eventTemp = eventDetails.get(i);
			EventDetails event = new EventDetails();
			event.setStartTime(eventTemp.getStartTime());
			event.setEndTime(eventTemp.getEndTime());
			event.setHost(eventTemp.getHost());
			event.setDescription(eventTemp.getDescription());
			event.setOrganizationID(eventTemp.getOrganizationID());
			event.setContactWebSite(eventTemp.getContactWebSite());
			event.setAddress2(eventTemp.getAddress2());
			event.setAddress1(eventTemp.getAddress1());
			event.setCityId(eventTemp.getCityId());
			event.setContactEmail(eventTemp.getContactEmail());
			event.setFromDate(eventTemp.getFromDate());
			event.setCountryID(eventTemp.getCountryID());
			event.setContactFB(eventTemp.getContactFB());
			event.setEventLocation(eventTemp.getEventLocation());
			event.setModifiedDate(eventTemp.getModifiedDate());
			event.setEventId(eventTemp.getEventId());
			event.setOtherInfo(eventTemp.getOtherInfo());
			event.setStateid(eventTemp.getStateid());
			event.setCreateDate(eventTemp.getCreateDate());
			event.setEventTypeId(eventTemp.getEventTypeId());
			event.setContactPhone(eventTemp.getContactPhone());
			event.setLocationLat(eventTemp.getLocationLat());
			event.setLocationLong(eventTemp.getLocationLong());
			event.setToDate(eventTemp.getToDate());
			event.setZipCode(eventTemp.getZipCode());
			event.setTitle(eventTemp.getTitle());
			
			
			String bucketName = s3BucketName + AppConstants.SLASH + eventImageBucketPath;
			String eventImagePath = null;
			String eventImage = eventTemp.getEventImage();
			if (eventImage != null) {
				String s3Obj = eventTemp.getEventId() + AppConstants.SLASH + eventImage;
				eventImagePath = generatePreSignedURL(bucketName, s3Obj);
				event.setEventImage(eventImagePath);
			} else {
				  String s3Obj = AppConstants.DEFAULT_ID + AppConstants.SLASH +
				  AppConstants.DEFAULT_NOTE_IMAGE; eventImagePath =
				  generatePreSignedURL(bucketName, s3Obj);
				  event.setEventImage(eventImagePath);
			}
			
			UserFavorateEvents userFavouriteEvent = eventFavorateRepository.findByUserIdAndEventId(userId, event.getEventId());
			if (userFavouriteEvent == null)
				event.setFavourite(false);
			else
				event.setFavourite(true);
			
			eventLists.add(event);
		}
		return eventLists;
	}
	
	@Override
	public int saveImage(int eventId, String imgName) {

		try {
			Query query = em.createNativeQuery("update Event set EventImage=" + imgName + " where EventId=" + eventId);

			int result = query.executeUpdate();
			return result;

		} catch (NoResultException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public String getEventImage(int eventId) {
		String eventImage = null;

		try {
			Query query = em.createNativeQuery("SELECT EventImage FROM EVENT WHERE EventId=:EventId")
					.setParameter("EventId", eventId);

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

		Query query = em.createQuery("DELETE FROM Event WHERE eventId = " + eventId);
		int deleted = query.executeUpdate();

		if (deleted > 0) {
			statusResponse.setStatus(true);
			statusResponse.setMessage("Event Deleted Sucessfully");
		} else {
			statusResponse.setStatus(false);
			statusResponse.setMessage("Event Deletion is Failure");
		}

		return statusResponse;
	}

	@Override
	public Upload uploadEventImage(InputStream inputStream, String imgName, String imgToDelete, int eventId) {
		Upload upload = null;

		try {

			AWSCredentials credentials = new BasicAWSCredentials(awsAccessKeyId, awsAccessSecretKey);
			AmazonS3 s3client = new AmazonS3Client(credentials);

			TransferManager manager = new TransferManager(credentials);
			ObjectMetadata meta = new ObjectMetadata();

			byte[] resultByte = IOUtils.toByteArray(inputStream);
			ByteArrayInputStream bis = new ByteArrayInputStream(resultByte);
			meta.setContentLength(resultByte.length);
			meta.setContentType(bis.toString());

			logger.info("length: {}", resultByte.length);
			String filePath = AppConstants.EVENTS_FOLDER + AppConstants.SLASH + eventId + AppConstants.SLASH + imgName;
			s3client.deleteObject(s3BucketName,
					AppConstants.EVENTS_FOLDER + AppConstants.SLASH + eventId + AppConstants.SLASH + imgToDelete);

			upload = manager.upload(s3BucketName, filePath, bis, meta);
			s3client.setObjectAcl(s3BucketName, filePath, CannedAccessControlList.Private);

			if (upload.isDone() == false) {
				logger.info("Transfer: {}", upload.getDescription());
				logger.info("  - State: {}", upload.getState());
				logger.info("  - Transfer progress percentage: {}",
						upload.getProgress().getTotalBytesToTransfer() + "%");
				logger.info("  - Bytes Transfered: {}", upload.getProgress().getBytesTransferred());
				Thread.sleep(200);
			}
			upload.waitForCompletion();
			manager.shutdownNow();

		} catch (AmazonServiceException ase) {
			logger.error("Caught an AmazonServiceException, which " + "means your request made it "
					+ "to Amazon S3, but was rejected with an error response" + " for some reason.");
			logger.error("Error Message:    {}", ase.getMessage());
			logger.error("HTTP Status Code: {}", ase.getStatusCode());
			logger.error("AWS Error Code:   {}", ase.getErrorCode());
			logger.error("Error Type:       {}", ase.getErrorType());
			logger.error("Request ID:       {}", ase.getRequestId());
		} catch (AmazonClientException ace) {
			logger.error("Caught an AmazonClientException, which " + "means the client encountered "
					+ "an internal error while trying to " + "communicate with S3, "
					+ "such as not being able to access the network.");
			logger.error("Error Message: {}", ace.getMessage());
		} catch (IOException e) {
			logger.error("Error {}", e.getMessage());
		} catch (InterruptedException e) {
			logger.error("Error {}", e.getMessage());
		}
		return upload;
	}

	@Override
	public InputStream getEventImageAsStream(int id) {

		String imageName = null;

		try {
			Query query = em.createNativeQuery("select EventImage from Event where EventId=" + id);

			if (query.getSingleResult() != null) {
				imageName = (String) query.getSingleResult();
			}
		} catch (NoResultException e) {
		}

		String folderPath = null;
		if (imageName != null) {
			folderPath = AppConstants.EVENTS_FOLDER + AppConstants.SLASH + id + AppConstants.SLASH + imageName;
		} else {
			folderPath = AppConstants.EVENTS_FOLDER + AppConstants.SLASH + AppConstants.DEFAULT_ID + AppConstants.SLASH
					+ AppConstants.DEFAULT_ORG_IMAGE;
		}
		GetObjectRequest objectRequest = new GetObjectRequest(s3BucketName, folderPath);

		InputStream s3IStream = null;
		try {
			S3Object s3object = getAmazonS3Client().getObject(objectRequest);
			s3IStream = s3object.getObjectContent();

		} catch (AmazonServiceException ase) {
			logger.error("Caught an AmazonServiceException, which " + "means your request made it "
					+ "to Amazon S3, but was rejected with an error response" + " for some reason.");
			logger.error("Error Message:    {}", ase.getMessage());
			logger.error("HTTP Status Code: {}", ase.getStatusCode());
			logger.error("AWS Error Code:   {}", ase.getErrorCode());
			logger.error("Error Type:       {}", ase.getErrorType());
			logger.error("Request ID:       {}", ase.getRequestId());
			return null;
		} catch (AmazonClientException ace) {
			logger.error("Caught an AmazonClientException, which " + "means the client encountered "
					+ "an internal error while trying to " + "communicate with S3, "
					+ "such as not being able to access the network.");
			logger.error("Error Message: {}", ace.getMessage());
			return null;
		}
		return s3IStream;
	}

	private AmazonS3 getAmazonS3Client() {

		AWSCredentials credentials = new BasicAWSCredentials(awsAccessKeyId, awsAccessSecretKey);
		return new AmazonS3Client(credentials);
	}

	@Override
	public StatusResponse favorateEvent(UserFavorateEvents favouriteEvent) {

		StatusResponse statusResponse = new StatusResponse();

		try {
			if(favouriteEvent.getEventId()!=0 || favouriteEvent.getUserId() !=0)
			{
				UserFavorateEvents userFavouriteEvent = eventFavorateRepository.findByUserIdAndEventId(favouriteEvent.getUserId(), favouriteEvent.getEventId());
				if(userFavouriteEvent == null){
				eventFavorateRepository.save(favouriteEvent);
				statusResponse.setStatus(true);
				statusResponse.setMessage("Successfully Updated the Status");
				}else{
					statusResponse.setStatus(false);
					statusResponse.setMessage("Event is Already Favourite");
				}
			}else{
				statusResponse.setStatus(false);
				statusResponse.setMessage("Invalid Input");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return statusResponse;
	}


	@Override
	public StatusResponse unfavorateEvent(UserFavorateEvents favorateEvent) {
		
		StatusResponse statusResponse = new StatusResponse();

		Query query = em.createQuery("DELETE FROM UserFavorateEvents WHERE eventId = " + favorateEvent.getEventId()+" AND userId = "+favorateEvent.getUserId());
		int deleted = query.executeUpdate();

		if (deleted > 0) {
			statusResponse.setStatus(true);
			statusResponse.setMessage("Sucessfully Unfavorated the Event");
		} else {
			statusResponse.setStatus(false);
			statusResponse.setMessage("Failure in Unfavorated the Event");
		}

		
		return statusResponse;
	}

	@Override
	public List<Event> getUserFavorateEvents(int userId) {

		List<Event> favouriteEvents = new ArrayList<Event>();
		
		TypedQuery<UserFavorateEvents> query = (TypedQuery<UserFavorateEvents>) em
				.createNativeQuery("SELECT * FROM UserFavorateEvents WHERE userId=" + userId, UserFavorateEvents.class);
		List<UserFavorateEvents> eventList = (List<UserFavorateEvents>) query.getResultList();
		
		for (int i = 0; i < eventList.size(); i++) {
			Event favouriteEvent = new Event();
			System.out.println(eventList.get(i).getEventId());
			String QUERY_GET_EVENT = "SELECT * FROM Event WHERE eventId =" + eventList.get(i).getEventId();

			TypedQuery<Event> eventDetailsObj = (TypedQuery<Event>) em.createNativeQuery(QUERY_GET_EVENT, Event.class);

			Event eventDetails = (Event) eventDetailsObj.getSingleResult();
			favouriteEvent.setEventId(eventDetails.getEventId());
			favouriteEvent.setTitle(eventDetails.getTitle());
			favouriteEvent.setStartTime(eventDetails.getStartTime());
			favouriteEvent.setEndTime(eventDetails.getEndTime());
			favouriteEvent.setDescription(eventDetails.getDescription());
			favouriteEvent.setFromDate(eventDetails.getFromDate());
			favouriteEvent.setAddress1(eventDetails.getAddress1());
			favouriteEvent.setAddress2(eventDetails.getAddress2());
			favouriteEvent.setCityId(eventDetails.getCityId());
			favouriteEvent.setContactEmail(eventDetails.getContactEmail());
			favouriteEvent.setContactFB(eventDetails.getContactFB());
			favouriteEvent.setContactPhone(eventDetails.getContactPhone());
			favouriteEvent.setContactWebSite(eventDetails.getContactWebSite());
			favouriteEvent.setCountryID(eventDetails.getCountryID());
			favouriteEvent.setCreateDate(eventDetails.getCreateDate());
			favouriteEvent.setEventLocation(eventDetails.getEventLocation());
			favouriteEvent.setLocationLat(eventDetails.getLocationLat());
			favouriteEvent.setLocationLong(eventDetails.getLocationLong());
			favouriteEvent.setModifiedDate(eventDetails.getModifiedDate());
			favouriteEvent.setOrganizationID(eventDetails.getOrganizationID());
			favouriteEvent.setOtherInfo(eventDetails.getOtherInfo());
			favouriteEvent.setStateid(eventDetails.getStateid());
			favouriteEvent.setZipCode(eventDetails.getZipCode());
			favouriteEvent.setEventImage(eventDetails.getEventImage());
			favouriteEvent.setHost(eventDetails.getHost());
			favouriteEvents.add(favouriteEvent);

		}
		
		return favouriteEvents;
	}

	

}

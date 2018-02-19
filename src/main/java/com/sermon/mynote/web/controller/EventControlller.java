package com.sermon.mynote.web.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.transfer.Upload;
import com.sermon.mynote.domain.Event;
import com.sermon.mynote.domain.EventDetails;
import com.sermon.mynote.domain.UserFavorateEvents;
import com.sermon.mynote.domain.EventInput;
import com.sermon.mynote.domain.EventType;
import com.sermon.mynote.domain.OrganizationUsers;
import com.sermon.mynote.domain.StatusMsg;
import com.sermon.mynote.domain.StatusResponse;
import com.sermon.mynote.service.EventsService;
import com.sermon.util.AppConstants;

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
	
	
	@RequestMapping(value = "/createFavorateEvent", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Event createFavorateEvent(@RequestBody Event eventInput) {

		
		return eventService.createEvent(eventInput);
	}
	

	
	@RequestMapping(value = "/getEventDetails", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public EventDetails getEventDetails(@RequestBody EventInput eventInput) {
        System.out.println("GET EVENT DETAILS");
		
		return eventService.getEventDetails(eventInput.getEventId());
	}
	
	
	@RequestMapping(value = "/getEventDetailsBasedOnOrg/{organizationId}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Event> getEventDetailsBasedOnOrg(@PathVariable int organizationId) {
		
		return eventService.getEventsByOrg(organizationId);
		//eventService.getEventDetails(eventInput.getEventId());
	}
	
	
	@RequestMapping(value = "/getEvents/{userId}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<EventDetails> getEventsList(@PathVariable int userId) {
        System.out.println("GET EVENT DETAILS");
		
		return eventService.getEventsList(userId);
	}
	
	
	/* update */
	@RequestMapping(value = "/updateEvent", method = RequestMethod.POST, produces = "application/json", consumes="application/json")
	@ResponseBody
	public StatusResponse updateEvent(@RequestBody Event event) {

		Event eventTemp = new Event();
		/*
		 * Date date = new Date(); note.setEventDate(new
		 * Timestamp(date.getTime())); note.setEventTime(new
		 * Timestamp(date.getTime()));
		 */

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
	
	
	@RequestMapping(value = "/UploadImage/{eventId}", method = RequestMethod.POST )
	@ResponseBody
	public StatusMsg continueFileUpload(@PathVariable int eventId, HttpServletRequest request,
			HttpServletResponse response) {
		MultipartHttpServletRequest mRequest;
		MultipartFile mFile = null;
		StatusMsg statusMsg = new StatusMsg();
		String imgName = null;
		logger.info("noteId : " + eventId);
		try {
			mRequest = (MultipartHttpServletRequest) request;
			mRequest.getParameterMap();

			Iterator<String> itr = mRequest.getFileNames();
			while (itr.hasNext()) {
				mFile = mRequest.getFile(itr.next());
				imgName = mFile.getOriginalFilename();
				logger.info("filename : " + imgName + " size : " + mFile.getSize());
			}

			String existingNoteImgName = eventService.getEventImage(eventId);
			String imgToDelete = null;
			if (existingNoteImgName != null) {
				imgToDelete = existingNoteImgName;
			}

			// convert image to jpg
			BufferedImage image = ImageIO.read(mFile.getInputStream());
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(image, "jpg", baos);
			InputStream fis = new ByteArrayInputStream(baos.toByteArray());

			String temp = imgName.substring(0, imgName.lastIndexOf('.'));
			String imageName = temp + ".jpg";

			Upload myUpload = eventService.uploadEventImage(fis, imageName, imgToDelete, eventId);

			myUpload.waitForCompletion();
			if (myUpload.isDone())
				eventService.saveImage(eventId, imageName);

			statusMsg.setStatus(AppConstants.FILES_UPLOAD);
			return statusMsg;
		} catch (Exception e) {
			e.printStackTrace();
			statusMsg.setStatus(AppConstants.ERROR_INTERNAL);
			return statusMsg;
		}
	}
	
	
	@RequestMapping(value = "/getEventImage/{id}", method = RequestMethod.GET, produces = "image/jpeg")
	@ResponseBody
	public byte[] getEventImage(@PathVariable int id) throws IOException {

		InputStream inputStream = eventService.getEventImageAsStream(id);

		byte[] bytes = null;
		String extension = null;

		S3ObjectInputStream s3InputStream = null;
		if (inputStream != null) {
			try {
				s3InputStream = (S3ObjectInputStream) inputStream;
				extension = s3InputStream.getHttpRequest().getURI().getPath();
				extension = extension.substring(extension.lastIndexOf(".") + 1);
				bytes = IOUtils.toByteArray(s3InputStream);
				int size = bytes.length;
				logger.info("image size : " + size);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				s3InputStream.close();
			}
			return bytes;
		} else {
			return bytes;
		}

	}
	
	

	@RequestMapping(value = "/favorateEvent", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	private StatusResponse favorateOrUnfavorateEvent(@RequestBody UserFavorateEvents favorateEvent) {
		
		return eventService.favorateEvent(favorateEvent);
	}
	
	
	@RequestMapping(value = "/unfavorateEvent", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	private StatusResponse unfavorateOrUnfavorateEvent(@RequestBody UserFavorateEvents favorateEvent) {
		
		return eventService.unfavorateEvent(favorateEvent);
	}
	
	
	/*@RequestMapping(value = "/getFavorateEvents", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	private StatusResponse getFavorateEvent(@RequestBody EventFavourite favorateEvent) {
		
		return eventService.getUserFavorateEvents(favorateEvent);
	}*/
	
	@RequestMapping(value = "/getFavorateEvents/{userId}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Event> getOrgUsrById(@PathVariable int userId) {
		logger.info("Listing user favorite");

		List<Event> userFavorites = eventService.getUserFavorateEvents(userId);
		return userFavorites;
	}
	
	
	
}

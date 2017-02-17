package com.sermon.mynote.web.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
import com.sermon.mynote.domain.AddNote;
import com.sermon.mynote.domain.AddSection;
import com.sermon.mynote.domain.AddSubSection;
import com.sermon.mynote.domain.Note;
import com.sermon.mynote.domain.PublishSchedule;
import com.sermon.mynote.domain.StatusMsg;
import com.sermon.mynote.domain.StatusResponse;
import com.sermon.mynote.service.NoteService;
import com.sermon.mynote.service.UserService;
import com.sermon.mynote.service.VwOrganizationInfoService;
import com.sermon.util.AppConstants;

@RequestMapping("/note")
@Controller
public class NoteController {

	final Logger logger = LoggerFactory.getLogger(NoteController.class);

	@Autowired
	MessageSource messageSource;

	@Autowired
	private NoteService noteService;

	@Autowired
	private UserService userService;

	@Autowired
	private VwOrganizationInfoService vwOrganizationInfoService;

	@RequestMapping(value = "/addNote", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody Note PostNote(@RequestBody Note note) {

		Note newNote = noteService.save(note);

		userService.updateUserRole(note.getAuthorId(), 2);

		return newNote;
	}

	/* update */
	@RequestMapping(value = "/updateNote/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public StatusResponse updateNote(@RequestBody AddNote note, @PathVariable Long id) {

		Note noteTemp = new Note();
		/*
		 * Date date = new Date(); note.setEventDate(new
		 * Timestamp(date.getTime())); note.setEventTime(new
		 * Timestamp(date.getTime()));
		 */

		noteTemp = noteService.findById(id.intValue());

		noteTemp.setAuthorId(note.getAuthorId());
		noteTemp.setCategoryId(note.getCategoryId());
		noteTemp.setEventDate(note.getEventDate());
		noteTemp.setEventTime(note.getEventTime());
		noteTemp.setIntroduction(note.getIntroduction());
		List<String> keywords = note.getKeywords();
		String keyword = StringUtils.join(keywords, ',');
		noteTemp.setKeywords(keyword);
		noteTemp.setSubTitle(note.getSubTitle());
		noteTemp.setTitle(note.getTitle());
		noteTemp.setOrganizationId(note.getOrganizationId());
		noteTemp.setPreacherName(note.getPreacherName());

		Note noteResponse = noteService.save(noteTemp);

		StatusResponse response = new StatusResponse();

		if (noteResponse != null)
			response.setStatus(true);
		else
			response.setStatus(false);

		return response;

	}

	@RequestMapping(value = "/deleteNote/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public StatusResponse delete(@PathVariable Long id) {
		logger.info("Deleting note with id: " + id);
		Note noteTemp = noteService.findById(id.intValue());
		int result = noteService.delete(noteTemp);
		logger.info("Note deleted successfully");
		StatusResponse status = new StatusResponse();
		if (result > 0) {
			status.setStatus(false);
		} else {
			status.setStatus(true);
		}

		return status;
	}

	@RequestMapping(value = "/searchNote/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Note getNoteById(@PathVariable int id) {
		logger.info("Listing note");

		Note note = noteService.findById(id);
		return note;
	}

	@RequestMapping(value = "/getNoteDetails", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Note getNoteDetails() {

		Note note = new Note();
		note.setAuthorId(1);
		note.setCategoryId(1);
		note.setIntroduction("intro test");
		note.setKeywords("test");
		/*
		 * Date date = new Date(); note.setEventDate(new
		 * Timestamp(date.getTime()));
		 */
		note.setSubTitle("subtitle test");
		note.setTitle("test");
		note.setOrganizationId(2);
		note.setGroupId(1);
		note.setPublished("Y");
		return note;
	}

	@RequestMapping(value = "/getNotesListByAuthorId/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Note> getChurchesByOrganization(@PathVariable int id) {
		logger.info("Listing getListOfNotesForGivenAuthorId");

		List<Note> noteList = noteService.findNotesByAuthorId(id);
		return noteList;
	}

	@RequestMapping(value = "/publishLater", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody StatusResponse publishLater(@RequestBody PublishSchedule publishSchedule) {

		PublishSchedule schedule = noteService.publishLater(publishSchedule);

		StatusResponse status = new StatusResponse();
		if (schedule == null) {
			status.setStatus(false);
		} else {
			status.setStatus(true);
		}

		return status;
	}

	@RequestMapping(value = "/updatePublishNow", method = RequestMethod.POST)
	@ResponseBody
	public StatusResponse updatePublishNow(@RequestBody Note note) {

		int result = noteService.updatePublishNow(note.getNoteId(), note.getPublished());

		StatusResponse response = new StatusResponse();

		if (result == 0)
			response.setStatus(true);
		else
			response.setStatus(false);

		return response;
	}

	@RequestMapping(value = "/createNote", method = RequestMethod.POST)
	@ResponseBody
	public int createNote(@RequestBody AddNote note) {

		/*
		 * int subSectionLength = note.getSubSections().size(); for (int i = 0;
		 * i < subSectionLength; i++) {
		 * logger.info(note.getSubSections().get(i).getSubsectionText()); }
		 */
		int result = noteService.createNote(note);

		return result;
	}

	@RequestMapping(value = "/getAddNoteDetails", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public AddNote getAddNoteDetails() {

		AddNote note = new AddNote();
		note.setAuthorId(288);
		note.setCategoryId(1);
		note.setIntroduction("intro test");
		List<String> keywords = new ArrayList<String>();
		keywords.add("test");
		note.setKeywords(keywords);
		note.setSubTitle("subtitle test");
		note.setTitle("test");
		note.setOrganizationId(2);
		note.setGroupId(1);
		note.setPublished("N");
		note.setNoteId(1);

		List<AddSection> sections = new ArrayList<AddSection>();

		AddSection section1 = new AddSection();
		section1.setSectionId(1);
		section1.setNoteId(1);
		section1.setSectionText("example section");
		List<Integer> sectionKeywords = new ArrayList<Integer>();
		sectionKeywords.add(1);
		sectionKeywords.add(2);
		section1.setSectionKeyWords(sectionKeywords);
		sections.add(section1);

		AddSection section2 = new AddSection();
		section2.setNoteId(1);
		section2.setSectionId(2);
		section2.setSectionText("example section");
		List<Integer> sectionKeywords1 = new ArrayList<Integer>();
		sectionKeywords1.add(1);
		sectionKeywords1.add(2);
		section2.setSectionKeyWords(sectionKeywords1);
		sections.add(section2);

		note.setSections(sections);

		List<AddSubSection> subSections = new ArrayList<AddSubSection>();

		AddSubSection subSection1 = new AddSubSection();
		subSection1.setSectionId(1);
		subSection1.setSubsectionId(1);
		subSection1.setSubsectionText("example subsection");
		List<Integer> subSectionKeywords = new ArrayList<Integer>();
		subSectionKeywords.add(1);
		subSectionKeywords.add(2);
		subSection1.setSubsectionKeyWords(subSectionKeywords);
		subSections.add(subSection1);

		AddSubSection subSection2 = new AddSubSection();
		subSection2.setSectionId(1);
		subSection2.setSubsectionId(2);
		subSection2.setSubsectionText("example subsection");
		List<Integer> subSectionKeywords1 = new ArrayList<Integer>();
		subSectionKeywords1.add(1);
		subSectionKeywords1.add(2);
		subSection2.setSubsectionKeyWords(subSectionKeywords1);
		subSections.add(subSection2);

		AddSubSection subSection3 = new AddSubSection();
		subSection3.setSectionId(2);
		subSection3.setSubsectionId(1);
		subSection3.setSubsectionText("example subsection");
		List<Integer> subSectionKeywords2 = new ArrayList<Integer>();
		subSectionKeywords2.add(1);
		subSectionKeywords2.add(2);
		subSection3.setSubsectionKeyWords(subSectionKeywords2);
		subSections.add(subSection3);

		note.setSubSections(subSections);

		return note;

	}

	@RequestMapping(value = "/getNote/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public AddNote getNote(@PathVariable int id) {
		logger.info("Listing note");

		AddNote note = noteService.getNote(id);
		return note;
	}

	@RequestMapping(value = "/updatePublish", method = RequestMethod.POST, headers = {
			"Content-type=application/json" })
	public @ResponseBody StatusResponse updatePublish(@RequestBody PublishSchedule publishSchedule) {

		int result = noteService.updatePublish(publishSchedule);

		StatusResponse response = new StatusResponse();

		if (result == 0)
			response.setStatus(true);
		else
			response.setStatus(false);

		return response;
	}

	@RequestMapping(value = "/UploadImage/{noteId}", method = RequestMethod.POST)
	@ResponseBody
	public StatusMsg continueFileUpload(@PathVariable int noteId, HttpServletRequest request,
			HttpServletResponse response) {
		MultipartHttpServletRequest mRequest;
		MultipartFile mFile = null;
		StatusMsg statusMsg = new StatusMsg();
		String imgName = null;
		logger.info("noteId : " + noteId);
		try {
			mRequest = (MultipartHttpServletRequest) request;
			mRequest.getParameterMap();

			Iterator<String> itr = mRequest.getFileNames();
			while (itr.hasNext()) {
				mFile = mRequest.getFile(itr.next());
				imgName = mFile.getOriginalFilename();
				logger.info("filename : " + imgName + " size : " + mFile.getSize());
			}

			String existingNoteImgName = noteService.getNoteImage(noteId);
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

			Upload myUpload = noteService.upLoadNoteFiles(fis, imageName, imgToDelete, noteId);

			myUpload.waitForCompletion();
			if (myUpload.isDone())
				noteService.saveImage(noteId, imageName);

			statusMsg.setStatus(AppConstants.FILES_UPLOAD);
			return statusMsg;
		} catch (Exception e) {
			e.printStackTrace();
			statusMsg.setStatus(AppConstants.ERROR_INTERNAL);
			return statusMsg;
		}
	}

	@RequestMapping(value = "/getNoteImage/{id}", method = RequestMethod.GET, produces = "image/jpeg")
	@ResponseBody
	public byte[] getSermonImage(@PathVariable int id) throws IOException {

		InputStream inputStream = noteService.getUserDocumentAsStream(id);

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

	@RequestMapping(value = "/uploadImage/{noteId}/{orgId}", method = RequestMethod.POST)
	@ResponseBody
	public StatusMsg FileUpload(@PathVariable int noteId, @PathVariable int orgId, HttpServletRequest request,
			HttpServletResponse response) {
		MultipartHttpServletRequest mRequest;
		MultipartFile mFile = null;
		StatusMsg statusMsg = new StatusMsg();
		String imgName = null;
		logger.info("noteId : " + noteId);
		logger.info("orgId : " + orgId);
		try {
			mRequest = (MultipartHttpServletRequest) request;
			mRequest.getParameterMap();

			Iterator<String> itr = mRequest.getFileNames();
			while (itr.hasNext()) {
				mFile = mRequest.getFile(itr.next());
				imgName = mFile.getOriginalFilename();
				logger.info("filename : " + imgName + " size : " + mFile.getSize());
			}

			String existingNoteImgName = null;

			if (noteId > 0) {
				existingNoteImgName = noteService.getNoteImage(noteId);
			} else if (orgId > 0) {
				existingNoteImgName = vwOrganizationInfoService.getOrgImage(orgId);
			}

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

			Upload myUpload = noteService.upLoadFiles(fis, imageName, imgToDelete, noteId, orgId);
			myUpload.waitForCompletion();

			if (myUpload.isDone()) {

				if (noteId > 0)
					noteService.saveImage(noteId, imageName);
				else if (orgId > 0)
					vwOrganizationInfoService.saveImage(orgId, imgName);
			}

			statusMsg.setStatus(AppConstants.FILES_UPLOAD);
			return statusMsg;
		} catch (Exception e) {
			e.printStackTrace();
			statusMsg.setStatus(AppConstants.ERROR_INTERNAL);
			return statusMsg;
		}
	}

}

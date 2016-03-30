package com.sermon.mynote.web.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.sermon.mynote.domain.AddNote;
import com.sermon.mynote.domain.Note;
import com.sermon.mynote.domain.PublishSchedule;
import com.sermon.mynote.domain.Section;
import com.sermon.mynote.domain.StatusResponse;
import com.sermon.mynote.domain.SubSection;
import com.sermon.mynote.service.NoteService;
import com.sermon.mynote.service.UserService;

@RequestMapping("/note")
@Controller
public class noteController {

	final Logger logger = LoggerFactory.getLogger(noteController.class);

	@Autowired
	MessageSource messageSource;

	@Autowired
	private NoteService noteService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/addNote", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody Note PostNote(@RequestBody Note note) {

		Note newNote = noteService.save(note);

		userService.updateUserRole(note.getAuthorId(), 2);

		return newNote;
	}

	/* update */
	@RequestMapping(value = "/updateNote/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public StatusResponse updateNote(@RequestBody Note note, @PathVariable Long id) {

		Note noteTemp = new Note();
		Date date = new Date();
		note.setEventDate(new Timestamp(date.getTime()));
		note.setEventTime(new Timestamp(date.getTime()));

		noteTemp = noteService.findById(id.intValue());

		noteTemp.setAuthorId(note.getAuthorId());
		noteTemp.setCategoryId(note.getCategoryId());
		noteTemp.setEventDate(note.getEventDate());
		noteTemp.setEventTime(note.getEventTime());
		noteTemp.setIntroduction(note.getIntroduction());
		noteTemp.setKeywords(note.getKeywords());
		noteTemp.setSubTitle(note.getSubTitle());
		noteTemp.setTitle(note.getTitle());
		noteTemp.setOrganizationId(note.getOrganizationId());

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
	public StatusResponse createNote(@RequestBody AddNote note) {

		int result = noteService.createNote(note);

		StatusResponse response = new StatusResponse();

		if (result > 0)
			response.setStatus(true);
		else
			response.setStatus(false);

		return response;
	}

	@RequestMapping(value = "/getAddNoteDetails", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public AddNote getAddNoteDetails() {

		AddNote note = new AddNote();
		note.setAuthorId(288);
		note.setCategoryId(1);
		note.setIntroduction("intro test");
		note.setKeywords("test");
		note.setSubTitle("subtitle test");
		note.setTitle("test");
		note.setOrganizationId(2);
		note.setGroupId(1);
		note.setPublished("N");
		note.setNoteId(1);

		List<Section> sections = new ArrayList<Section>();

		Section section1 = new Section();
		section1.setSectionId(1);
		section1.setNoteId(1);
		section1.setSectionText("example section");
		section1.setSectionKeyWords("1,2");
		sections.add(section1);

		Section section2 = new Section();
		section2.setNoteId(1);
		section2.setSectionId(2);
		section2.setSectionText("example section");
		section2.setSectionKeyWords("1,2");
		sections.add(section2);

		note.setSections(sections);

		List<SubSection> subSections = new ArrayList<SubSection>();

		SubSection subSection1 = new SubSection();
		subSection1.setSectionId(1);
		subSection1.setSubsectionId(1);
		subSection1.setSubsectionText("example subsection");
		subSection1.setSubsectionKeyWords("1,2");
		subSections.add(subSection1);

		SubSection subSection2 = new SubSection();
		subSection2.setSectionId(1);
		subSection2.setSubsectionId(2);
		subSection2.setSubsectionText("example subsection");
		subSection2.setSubsectionKeyWords("1,2");
		subSections.add(subSection2);

		SubSection subSection3 = new SubSection();
		subSection3.setSectionId(2);
		subSection3.setSubsectionId(1);
		subSection3.setSubsectionText("example subsection");
		subSection3.setSubsectionKeyWords("1,2");
		subSections.add(subSection3);

		note.setSubSections(subSections);

		return note;

	}

}

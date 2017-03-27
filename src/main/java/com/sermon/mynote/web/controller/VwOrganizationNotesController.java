package com.sermon.mynote.web.controller;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sermon.mynote.domain.VwOrganizationNotes;
import com.sermon.mynote.service.VwOrganizationNotesService;

@RequestMapping("/sermons")
@Controller
public class VwOrganizationNotesController {

	@Autowired
	private VwOrganizationNotesService vwOrganizationNotesService;

	final Logger logger = LoggerFactory.getLogger(VwOrganizationNotesController.class);

	@RequestMapping(value = "/organization/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<VwOrganizationNotes> getChurchesByOrganization(@PathVariable int id) {
		logger.info("Listing getNotesByOrganization");

		List<VwOrganizationNotes> vwOrganizationNotes = vwOrganizationNotesService.findSermonsByOrgId(id);
		return vwOrganizationNotes;
	}

	@RequestMapping(value = "/organization", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<VwOrganizationNotes> getAllNoteSermons() {
		logger.info("Listing getAllNoteSermons()");

		List<VwOrganizationNotes> vwOrganizationNotes = vwOrganizationNotesService.findAll();
		return vwOrganizationNotes;
	}

	@RequestMapping(value = "/org/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<VwOrganizationNotes> getNotesByOrganization(@PathVariable int id) {
		logger.info("Listing getPublishedNotesByOrganization");

		List<VwOrganizationNotes> vwOrganizationNotes = vwOrganizationNotesService.findSermonsByOrgId(id);

		for (Iterator<VwOrganizationNotes> iterator = vwOrganizationNotes.iterator(); iterator.hasNext();) {
			VwOrganizationNotes notes = iterator.next();
			if (!notes.getPublished().equals("Y")) {
				iterator.remove();
			}
		}
		return vwOrganizationNotes;
	}
}

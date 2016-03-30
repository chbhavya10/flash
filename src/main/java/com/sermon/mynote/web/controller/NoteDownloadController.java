package com.sermon.mynote.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sermon.mynote.domain.NoteDownload;
import com.sermon.mynote.domain.StatusResponse;
import com.sermon.mynote.service.NoteDownloadService;

@RequestMapping("/noteDownload")
@Controller
public class NoteDownloadController {

	final Logger logger = LoggerFactory.getLogger(noteController.class);

	@Autowired
	private NoteDownloadService noteDownloadService;

	@RequestMapping(value = "/addNoteDownload", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody StatusResponse PostNoteDownload(
			@RequestBody NoteDownload noteDownload) {

		NoteDownload newNoteDownload = noteDownloadService.save(noteDownload);
		
		StatusResponse response=new StatusResponse();
		
		if(newNoteDownload !=null)
			response.setStatus(true);
		else
			response.setStatus(false);

		return response;
	}

}

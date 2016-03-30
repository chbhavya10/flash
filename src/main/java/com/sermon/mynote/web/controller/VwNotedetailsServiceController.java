package com.sermon.mynote.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sermon.mynote.domain.VwNotedetails;
import com.sermon.mynote.service.VwNotedetailsService;


@RequestMapping("/notedetail")
@Controller
public class VwNotedetailsServiceController {
	
	@Autowired
	private VwNotedetailsService vwNotedetailsService;
	
	final Logger logger = LoggerFactory.getLogger(organizationController.class);
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<VwNotedetails> getNoteDetailsById(@PathVariable int id) {	 
		logger.info("Listing contacts");	
		
		List<VwNotedetails> vwNotedetails = vwNotedetailsService.findNoteDetailsByNoteId(id);
		return vwNotedetails;
	}

}

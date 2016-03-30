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

import com.sermon.mynote.domain.User;
import com.sermon.mynote.domain.VwNoteSummary;
import com.sermon.mynote.service.VwNoteSummaryService;

@RequestMapping("/notelist")
@Controller
public class VwNoteSummaryServiceController {

	@Autowired
	private VwNoteSummaryService vwNoteSummaryService;
	
	final Logger logger = LoggerFactory.getLogger(organizationController.class);
	
	@RequestMapping(value = "/summary/{id}", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<VwNoteSummary> getNoteSummaryById(@PathVariable int id) {	 
		logger.info("Listing contacts");	
		
		List<VwNoteSummary> vwNoteSummary = vwNoteSummaryService.findNoteSummaryByNoteId(id);
		return vwNoteSummary;
	}
	
	
	
	@RequestMapping(value = "/summary", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<VwNoteSummary> getAllNoteSummary() {	 
		logger.info("Listing contacts");	
		
		List<VwNoteSummary> vwNoteSummary = vwNoteSummaryService.findAll();
		return vwNoteSummary;
	}
	
}

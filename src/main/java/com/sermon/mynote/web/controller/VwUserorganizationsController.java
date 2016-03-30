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

import com.sermon.mynote.domain.VwUserorganizations;
import com.sermon.mynote.service.VwUserorganizationsService;

@RequestMapping("/orglistuser")
@Controller
public class VwUserorganizationsController {

	@Autowired
	private VwUserorganizationsService vwUserorganizationsService;
	
	final Logger logger = LoggerFactory.getLogger(organizationController.class);
	
	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<VwUserorganizations> getChurchesByOrganization(@PathVariable int id) {	 
		logger.info("Listing contacts");	
		
		List<VwUserorganizations> vwUserorganizations = vwUserorganizationsService.findOrganizationsByUser(id);
		return vwUserorganizations;
	}
}

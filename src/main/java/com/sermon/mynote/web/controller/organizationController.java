package com.sermon.mynote.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sermon.mynote.domain.Organization;
import com.sermon.mynote.service.OrganizationService;



@RequestMapping("/organization")
@Controller
public class organizationController {
	
	@Autowired
	private OrganizationService organizationService;

	
	
	
	final Logger logger = LoggerFactory.getLogger(organizationController.class);
	
	@RequestMapping(value = "/users", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<Organization> getChurchesByOrganization() {	 
		logger.info("Listing contacts");	
		
		List<Organization> organization = organizationService.findChurchesByOrganization();
		return organization;
	}
	
	

}

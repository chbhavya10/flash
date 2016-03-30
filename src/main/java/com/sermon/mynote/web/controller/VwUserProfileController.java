package com.sermon.mynote.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sermon.mynote.domain.VwUserprofile;
import com.sermon.mynote.service.VwUserprofileService;

@RequestMapping("/findUserProfile")
@Controller
public class VwUserProfileController {
	
final Logger logger = LoggerFactory.getLogger(VwUserProfileController.class);	
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	private VwUserprofileService vwUserProfileService ;

	
	@RequestMapping(value = "/searchByUserId/{id}", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<VwUserprofile> getUserProfileByUserId(@PathVariable int id) {	 
		logger.info("Listing contacts");	
		
		List<VwUserprofile> vwUserprofile = vwUserProfileService.findUserProfileByUserId(id);
		return vwUserprofile;
	}
	
}

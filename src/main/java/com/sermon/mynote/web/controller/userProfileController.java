package com.sermon.mynote.web.controller;

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

import com.sermon.mynote.domain.StatusResponse;
import com.sermon.mynote.domain.UserProfile;
import com.sermon.mynote.service.UserProfileService;


@RequestMapping("/userProfile")
@Controller
public class userProfileController {
	
final Logger logger = LoggerFactory.getLogger(userProfileController.class);	
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	private UserProfileService userProfileService;
	
	
	@RequestMapping(value="/updateProfile", method=RequestMethod.POST)
    @ResponseBody
    public StatusResponse updateUserProfile(@RequestBody UserProfile userProfile) {
	
	/*
	@RequestMapping(value="/updateProfile/{id}", method = RequestMethod.PUT,headers = {"Content-type=application/json"})
public @ResponseBody UserProfile updateUserProfile(@RequestBody UserProfile userProfile,@PathVariable Long id) {
		*/
		//System.out.println("id.."+id);
		
		//UserProfile userProfiletemp=userProfileService.findById(id.intValue());

		//userProfile=userProfiletemp;
		//userProfileService.save(userProfiletemp);
		
		//System.out.println("userProfile...."+id);
		
		//Integer userId = id != null ? id.intValue() : null;
		
		//userProfile.setUserId(userId);
		
        int result = userProfileService.updateUserProfile(
        		userProfile.getUserId(),
        		userProfile.getFirstName(),
        		userProfile.getLastName(),
        		userProfile.getNickName(),
        		userProfile.getAddress1(), 
        		userProfile.getAddress2(), 
        		userProfile.getCityId(),
        		userProfile.getStateId(), 
        		userProfile.getCountryId(), 
        		userProfile.getZipCode()
        		//userProfile.getDOB(),
        		//userProfile.getGender()
        		);
        
        //return userProfile;
        StatusResponse status = new StatusResponse();
        if(result == 0){
			 status.setStatus(true);
		 }else{
			 status.setStatus(false);
		 }
		
		return status;
}
        
    }
	



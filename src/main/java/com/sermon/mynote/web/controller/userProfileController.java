package com.sermon.mynote.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sermon.mynote.domain.StatusResponse;
import com.sermon.mynote.domain.VwUserprofile;
import com.sermon.mynote.service.UserProfileService;
import com.sermon.mynote.service.UserService;

@RequestMapping("/userProfile")
@Controller
public class userProfileController {

	final Logger logger = LoggerFactory.getLogger(userProfileController.class);

	@Autowired
	MessageSource messageSource;

	@Autowired
	private UserProfileService userProfileService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/updateProfile", method = RequestMethod.POST)
	@ResponseBody
	public StatusResponse updateUserProfile(@RequestBody VwUserprofile userProfile) {

		/*
		 * @RequestMapping(value="/updateProfile/{id}", method =
		 * RequestMethod.PUT,headers = {"Content-type=application/json"})
		 * public @ResponseBody UserProfile updateUserProfile(@RequestBody
		 * UserProfile userProfile,@PathVariable Long id) {
		 */
		// System.out.println("id.."+id);

		// UserProfile
		// userProfiletemp=userProfileService.findById(id.intValue());

		// userProfile=userProfiletemp;
		// userProfileService.save(userProfiletemp);

		// System.out.println("userProfile...."+id);

		// Integer userId = id != null ? id.intValue() : null;

		// userProfile.setUserId(userId);

		int result = userProfileService.updateUserProfile(userProfile.getUserId(), userProfile.getFirstName(),
				userProfile.getLastName(), userProfile.getNickName(), userProfile.getAddress_1(),
				userProfile.getAddress_2(), userProfile.getCityId(), userProfile.getStateId(),
				userProfile.getCountryID(), userProfile.getZipCode()
		// userProfile.getDOB(),
		// userProfile.getGender()
		);

		userService.updateUser(userProfile.getUserId(), userProfile.getUserEmail(), userProfile.getUserName(),
				userProfile.getUserPhone());

		// return userProfile;
		StatusResponse status = new StatusResponse();
		if (result == 0) {
			status.setStatus(true);
		} else {
			status.setStatus(false);
		}

		return status;
	}

}

package com.sermon.mynote.web.controller;

import java.sql.Timestamp;
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

import com.sermon.mynote.domain.OrganizationGroup;
import com.sermon.mynote.domain.StatusMsg;
import com.sermon.mynote.domain.StatusResponse;
import com.sermon.mynote.domain.User;
import com.sermon.mynote.domain.UserProfile;
import com.sermon.mynote.domain.UserRegistration;
import com.sermon.mynote.domain.UserVerificationTokens;
import com.sermon.mynote.service.UserProfileService;
import com.sermon.mynote.service.UserService;

/**
 * @author Clarence
 *
 */
@RequestMapping("/userlist")
@Controller
public class UserController {

	final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	MessageSource messageSource;

	@Autowired
	private UserService userService;

	@Autowired
	private UserProfileService userProfileService;

	@RequestMapping(value = "/users", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<User> getAllUsers() {
		logger.info("Listing contacts");

		List<User> users = userService.findAll();
		return users;
	}

	@RequestMapping(value = "/validateUser/{username}/{password}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Integer getValidateUser(@PathVariable String username, @PathVariable String password) {
		logger.info("Validating user");

		return userService.validateUser(username, password);

	}

	@RequestMapping(value = "/addUser", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody User PostService(@RequestBody User user) {

		System.out.println("user:--" + user.getUserName());
		logger.debug(user.toString());
		User newUser = userService.save(user);

		return newUser;
	}

	@RequestMapping(value = "/addNewUser", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody StatusResponse PostServiceAddNewUser(@RequestBody User user) {

		System.out.println("user:--" + user.getUserName());
		logger.debug(user.toString());

		Date date = new Date();

		Timestamp currentDate = new Timestamp(date.getTime());

		boolean result = userService.createUser(user.getUserName(), user.getUserEmail(), user.getUserPassword(),
				currentDate);

		StatusResponse statusResponse = new StatusResponse();
		if (result == false) {
			statusResponse.setStatus(true);
		}
		return statusResponse;
	}

	/* update */
	@RequestMapping(value = "/updateUser/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public void updateUserFavorite(@RequestBody User user, @PathVariable Long id) {

		User usertemp = userService.findById(id.intValue());
		// user = usertemp;

		//usertemp.setUserEmail(user.getUserEmail());
		usertemp.setUserId(id.intValue());
		//usertemp.setUserName(user.getUserName());
		usertemp.setUserMobile(user.getUserMobile());

		logger.info("Updating user : " + user);
		int result = userService.updateUser(usertemp.getUserId(), usertemp.getUserEmail(), usertemp.getUserName(),
				usertemp.getUserMobile());
		System.out.println(result);
		// userService.save(usertemp);
		logger.info("Contact updated successfully with info: " + userService);
	}

	// update users password
	@RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
	@ResponseBody
	public StatusResponse updateUserPassword(@RequestBody User user) {

		int result = userService.updateUserPassword(user.getUserId(), user.getUserPassword());

		StatusResponse response = new StatusResponse();

		if (result == 0)
			response.setStatus(true);
		else
			response.setStatus(false);

		return response;
	}

	@RequestMapping(value = "/checkUserNameAvailability/{username}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public StatusResponse checkUserNameAvailability(@PathVariable String username) {
		logger.info("check username availability");

		int result = userService.checkUserNameAvailability(username);

		StatusResponse response = new StatusResponse();

		if (result > 0)
			response.setStatus(false);
		else
			response.setStatus(true);

		return response;
	}

	@RequestMapping(value = "/checkUserEmailAvailability", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public StatusResponse checkUserEmailAvailability(@RequestBody User user) {
		logger.info("check username availability");

		int result = userService.checkUserEmailAvailability(user.getUserEmail());

		StatusResponse response = new StatusResponse();

		if (result > 0)
			response.setStatus(false);
		else
			response.setStatus(true);

		return response;
	}

	@RequestMapping(value = "/getGroups", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<OrganizationGroup> getGroups() {
		logger.info("Listing groups");

		List<OrganizationGroup> groups = userService.getGroups();
		return groups;
	}

	@RequestMapping(value = "/registerUser", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody StatusResponse registerUser(@RequestBody UserRegistration user) {

		System.out.println("user:--" + user.getUserName());

		User newUser = new User();

		newUser.setUserEmail(user.getUserEmail());
		newUser.setUserName(user.getUserName());
		newUser.setUserPassword(user.getUserPassword());
		newUser.setUserMobile(user.getUserPhone());

		User resultUser = userService.save(newUser);
		int result = 0;

		if (resultUser != null) {

			String nickName = user.getNickName();

			UserProfile profile = new UserProfile();

			if (nickName == null) {
				profile.setNickName(user.getUserName());
			} else {
				profile.setNickName(user.getNickName());
			}

			profile.setUserId(resultUser.getUserId());
			profile.setFirstName(user.getFirstName());
			profile.setLastName(user.getLastName());

			profile.setAddress1(user.getAddress1());
			profile.setAddress2(user.getAddress2());
			profile.setCountryId(user.getCountryId());
			profile.setStateId(user.getStateId());
			profile.setCityId(user.getCityId());
			profile.setZipCode(user.getZipCode());
			profile.setDOB(user.getDOB());
			profile.setGender(user.getGender());

			result = userProfileService.updateUserProfile(profile.getUserId(), profile.getFirstName(),
					profile.getLastName(), profile.getNickName(), profile.getAddress1(), profile.getAddress2(),
					profile.getCityId(), profile.getStateId(), profile.getCountryId(), profile.getZipCode()
			// profile.getDOB(),
			// profile.getGender()
			);

		}

		StatusResponse statusResponse = new StatusResponse();
		if (result == 0) {
			statusResponse.setStatus(true);
		} else {
			statusResponse.setStatus(false);
		}
		return statusResponse;
	}

	@RequestMapping(value = "/forgotPassword", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public StatusResponse forgotPassword(@RequestBody UserVerificationTokens token) {
		logger.info("check username availability");

		int result = userService.forgotPassword(token.getVerificationToken(), token.getPassword());

		StatusResponse response = new StatusResponse();
		StatusMsg statusMsg = new StatusMsg();

		if (result == -1) {
			statusMsg.setStatus("Link Expired");
		} else if (result == 0)
			response.setStatus(true);
		else {
			response.setStatus(false);
		}

		return response;
	}

}

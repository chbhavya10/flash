package com.sermon.mynote.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sermon.mynote.domain.OrganizationFeedback;
import com.sermon.mynote.domain.StatusResponse;
import com.sermon.mynote.service.OrganizationFeedbackService;

@Controller
@RequestMapping("/orgFeedback")
public class OrganizationFeedbackController {

	final Logger logger = LoggerFactory.getLogger(OrganizationFeedbackController.class);

	@Autowired
	private OrganizationFeedbackService orgFeedbackService;

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public StatusResponse updateOrgFeedback(@RequestBody OrganizationFeedback organizationFeedback) {

		int result = orgFeedbackService.updateOrgFeedback(organizationFeedback.getOrganizationId(),
				organizationFeedback.getUserId(), organizationFeedback.getRating(),
				organizationFeedback.getFeedbackComments());

		StatusResponse response = new StatusResponse();

		if (result == 0)
			response.setStatus(true);
		else
			response.setStatus(false);

		return response;
	}

	@RequestMapping(value = "/getFeedback", method = RequestMethod.POST)
	@ResponseBody
	public OrganizationFeedback getFeedback(@RequestBody OrganizationFeedback organizationFeedback) {

		OrganizationFeedback feedback = orgFeedbackService.getFeedback(organizationFeedback.getOrganizationId(),
				organizationFeedback.getUserId());

		return feedback;
	}

}

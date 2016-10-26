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

import com.sermon.mynote.domain.Request;
import com.sermon.mynote.domain.RequestType;
import com.sermon.mynote.domain.StatusResponse;
import com.sermon.mynote.service.RequestService;

@Controller
@RequestMapping("/request")
public class RequestController {

	final Logger logger = LoggerFactory.getLogger(StatesController.class);

	@Autowired
	MessageSource messageSource;

	@Autowired
	private RequestService requestService;

	@RequestMapping(value = "/getRequestTypes", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<RequestType> getRequestTypes() {

		List<RequestType> requestTypes = requestService.findAll();

		return requestTypes;
	}

	@RequestMapping(value = "/addRequest", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public StatusResponse addRequest(@RequestBody Request request) {

		Request resultRequest = requestService.save(request);

		StatusResponse response = new StatusResponse();

		if (resultRequest != null)
			response.setStatus(true);
		else
			response.setStatus(false);

		return response;

	}

	@RequestMapping(value = "/getRequest/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Request getRequest(@PathVariable int id) {

		Request resultRequest = requestService.findById(id);
		return resultRequest;

	}

	@RequestMapping(value = "/updateRequest", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public StatusResponse updateRequest(@RequestBody Request request) {

		if (request.getRequestStatus() == null || request.getRequestStatus().isEmpty()) {
			
			Request requestDetails = requestService.findById(request.getRequestId());
			request.setRequestStatus(requestDetails.getRequestStatus());
		}

		int result = requestService.updateRequest(request.getRequestId(), request.getRequestUpdate(),
				request.getRequestStatus());

		StatusResponse response = new StatusResponse();

		if (result == 0)
			response.setStatus(true);
		else
			response.setStatus(false);

		return response;

	}

}

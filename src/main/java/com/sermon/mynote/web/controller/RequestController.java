package com.sermon.mynote.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sermon.mynote.domain.RequestType;
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

}

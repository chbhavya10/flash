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

import com.sermon.mynote.domain.VwUserRequests;
import com.sermon.mynote.service.VwUserRequestsService;

@Controller
@RequestMapping("/requests")
public class VwUserRequestsController {

	final Logger logger = LoggerFactory.getLogger(VwUserRequestsController.class);

	@Autowired
	private VwUserRequestsService vwUserRequestsService;

	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<VwUserRequests> getRequestsByUserId(@PathVariable int id) {

		List<VwUserRequests> vwUserRequests = vwUserRequestsService.findRequestsByUserId(id);
		return vwUserRequests;

	}

	@RequestMapping(value = "/org/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<VwUserRequests> getRequestsByOrgId(@PathVariable int id) {

		List<VwUserRequests> vwUserRequests = vwUserRequestsService.findRequestsByOrgId(id);
		return vwUserRequests;
	}

}

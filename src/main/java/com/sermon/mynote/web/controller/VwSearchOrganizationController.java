package com.sermon.mynote.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sermon.mynote.domain.LimitParameters;
import com.sermon.mynote.domain.OrganizationLike;
import com.sermon.mynote.domain.SearchOrganizationResult;
import com.sermon.mynote.domain.User;
import com.sermon.mynote.service.VwSearchOrganizationService;

@RequestMapping("/searchorg")
@Controller
public class VwSearchOrganizationController {

	@Autowired
	private VwSearchOrganizationService vwSearchOrganizationService;

	@RequestMapping(value = "/search/{orgname}/{zipcode}/{city}/{denomination}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<OrganizationLike> SearchOrganiz(@PathVariable String orgname, @PathVariable String zipcode,
			@PathVariable String city, @PathVariable String denomination) {

		List<OrganizationLike> searchOrganization = vwSearchOrganizationService.SearchOrganiz(orgname, zipcode, city,
				denomination);
		return searchOrganization;
		
	}
	
	@RequestMapping(value = "/search/{orgname}/{zipcode}/{city}/{denomination}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public SearchOrganizationResult SearchOrganiz(@PathVariable String orgname, @PathVariable String zipcode,
			@PathVariable String city, @PathVariable String denomination,
			@RequestBody LimitParameters limitParameters) {

		SearchOrganizationResult searchOrganization = vwSearchOrganizationService.SearchOrganiz(orgname, zipcode, city,
				denomination, limitParameters);
		return searchOrganization;
	}

	
	@RequestMapping(value = "/searchByUser/{orgname}/{zipcode}/{city}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public List<OrganizationLike> searchOrganization(@PathVariable String orgname, @PathVariable String zipcode,
			@PathVariable String city, @RequestBody User userId) {

		List<OrganizationLike> searchOrganization = vwSearchOrganizationService.SearchOrganization(orgname, zipcode,
				city, userId.getUserId());
		return searchOrganization;
	}
	
	@RequestMapping(value = "/search/{orgname}/{zipcode}/{city}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public SearchOrganizationResult SearchOrganiz(@PathVariable String orgname, @PathVariable String zipcode,
			@PathVariable String city, @RequestBody LimitParameters limitParameters) {

		SearchOrganizationResult searchOrganization = vwSearchOrganizationService.SearchOrganiz(orgname, zipcode, city,
				limitParameters);
		return searchOrganization;
	}

	
	
	

}

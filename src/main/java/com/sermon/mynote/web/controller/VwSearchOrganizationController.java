package com.sermon.mynote.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sermon.mynote.domain.SearchOrganization;
import com.sermon.mynote.service.VwSearchOrganizationService;

@RequestMapping("/searchorg")
@Controller
public class VwSearchOrganizationController {
	
	@Autowired 
	private VwSearchOrganizationService vwSearchOrganizationService;

	
	
	@RequestMapping(value = "/search/{orgname}/{zipcode}/{city}", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<SearchOrganization> SearchOrganiz(@PathVariable  String orgname,@PathVariable String zipcode,@PathVariable String city) {	 
			
	
		
		List<SearchOrganization> searchOrganization = vwSearchOrganizationService.SearchOrganiz(orgname,zipcode,city);
		return searchOrganization;
	}
}

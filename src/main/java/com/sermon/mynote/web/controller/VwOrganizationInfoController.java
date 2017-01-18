package com.sermon.mynote.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sermon.mynote.domain.StatusResponse;
import com.sermon.mynote.domain.VwOrganizationInfo;
import com.sermon.mynote.service.OrganizationService;
import com.sermon.mynote.service.VwOrganizationInfoService;

@RequestMapping("/orgInfo")
@Controller
public class VwOrganizationInfoController {

	final Logger logger = LoggerFactory.getLogger(VwOrganizationInfoController.class);

	@Autowired
	private VwOrganizationInfoService vwOrganizationInfoService;

	@Autowired
	private OrganizationService organizationService;

	@RequestMapping(value = "/searchByOrgId/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<VwOrganizationInfo> getOrganizationInfoByOrgId(@PathVariable int id) {
		logger.info("Listing contacts");

		List<VwOrganizationInfo> vwOrgInfo = vwOrganizationInfoService.findOrganizationInfoByOrgId(id);
		return vwOrgInfo;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public StatusResponse updateOrgInfo(@RequestBody VwOrganizationInfo orgInfo) {

		int result = vwOrganizationInfoService.updateOrgInfo(orgInfo.getOrganizationId(), orgInfo.getWebsite(),
				orgInfo.getPrimaryEmail(), orgInfo.getGeneralInfo(), orgInfo.getHours(), orgInfo.getFacebookLink());

		organizationService.updateOrganization(orgInfo.getOrganizationId(), orgInfo.getAddress1(),
				orgInfo.getAddress2(), orgInfo.getCityId(), orgInfo.getStateId(), orgInfo.getCountryID(),
				orgInfo.getZipCode());

		StatusResponse status = new StatusResponse();
		if (result == 0) {
			status.setStatus(true);
		} else {
			status.setStatus(false);
		}

		return status;
	}

}

package com.sermon.mynote.service;

import java.util.List;

import com.sermon.mynote.domain.LimitParameters;
import com.sermon.mynote.domain.OrganizationLike;
import com.sermon.mynote.domain.SearchOrganizationResult;

public interface VwSearchOrganizationService {

	public List<OrganizationLike> SearchOrganiz(String organizationName, String zipCode, String city,
			String denomination);
	
	public SearchOrganizationResult SearchOrganiz(String orgname, String zipcode, String city, String denomination,
			LimitParameters limitParameters);

	public List<OrganizationLike> SearchOrganization(String orgname, String zipcode, String city, Integer userId);
	
	public SearchOrganizationResult SearchOrganiz(String orgname, String zipcode, String city,
			LimitParameters limitParameters);



	
}

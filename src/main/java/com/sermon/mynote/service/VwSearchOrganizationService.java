package com.sermon.mynote.service;

import java.util.List;

import com.sermon.mynote.domain.SearchOrganization;

public interface VwSearchOrganizationService {

	public List<SearchOrganization> SearchOrganiz(String organizationName,String zipCode,String city);
}

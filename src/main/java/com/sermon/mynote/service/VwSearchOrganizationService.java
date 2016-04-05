package com.sermon.mynote.service;

import java.util.List;

import com.sermon.mynote.domain.OrganizationLike;

public interface VwSearchOrganizationService {

	public List<OrganizationLike> SearchOrganiz(String organizationName, String zipCode, String city);
}

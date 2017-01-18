package com.sermon.mynote.service;

import java.util.List;

import com.sermon.mynote.domain.Organization;

public interface OrganizationService {

	public List<Organization> findChurchesByOrganization();

	public int updateOrganization(int organizationId, String address1, String address2, int cityId, int stateId,
			int countryID, String zipCode);

}

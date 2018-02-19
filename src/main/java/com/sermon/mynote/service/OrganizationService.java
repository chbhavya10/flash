package com.sermon.mynote.service;

import java.util.List;

import com.sermon.mynote.domain.Organization;
import com.sermon.mynote.domain.VwOrganizationInfo;

public interface OrganizationService {

	public List<Organization> findChurchesByOrganization();

	public int updateOrganization(VwOrganizationInfo orgInfo);
}

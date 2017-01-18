package com.sermon.mynote.service;

import java.util.List;

import com.sermon.mynote.domain.VwOrganizationInfo;

public interface VwOrganizationInfoService {

	List<VwOrganizationInfo> findOrganizationInfoByOrgId(int orgId);

	int updateOrgInfo(int organizationId, String website, String primaryEmail, String generalInfo, String hours,
			String facebookLink);

}

package com.sermon.mynote.service;

import java.io.InputStream;
import java.util.List;

import com.amazonaws.services.s3.transfer.Upload;
import com.sermon.mynote.domain.VwOrganizationInfo;

public interface VwOrganizationInfoService {

	List<VwOrganizationInfo> findOrganizationInfoByOrgId(int orgId);

	int updateOrgInfo(int organizationId, String website, String primaryEmail, String generalInfo, String hours,
			String facebookLink);

	String getOrgImage(int orgId);

	int saveImage(int orgId, String imageName);

	Upload uploadOrgFiles(InputStream fis, String imageName, String imgToDelete, int orgId);

	InputStream getOrgImageAsStream(int id);

}

package com.sermon.mynote.service;

import java.io.InputStream;
import java.util.List;

import com.amazonaws.services.s3.transfer.Upload;
import com.sermon.mynote.domain.VwOrgInfo;
import com.sermon.mynote.domain.VwOrganizationInfo;

public interface VwOrganizationInfoService {

	List<VwOrgInfo> findOrganizationInfoByOrgId(int orgId);

	int updateOrgInfo(VwOrganizationInfo orgInfo);
	
	 int getLikeCount(int orgId);
	 
	 int getDownloadCount(int orgId);

	 int getSermonCount(int orgId);

	String getOrgImage(int orgId);

	int saveImage(int orgId, String imageName);

	Upload uploadOrgFiles(InputStream fis, String imageName, String imgToDelete, int orgId);

	InputStream getOrgImageAsStream(int id);

}

package com.sermon.mynote.service;

import java.util.List;

import com.sermon.mynote.domain.UserOrgList;

public interface VwUserorganizationsService {

	public List<UserOrgList> findOrganizationsByUser(int userid);
	
}

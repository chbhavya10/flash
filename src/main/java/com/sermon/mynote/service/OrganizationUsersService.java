package com.sermon.mynote.service;

import java.util.List;

import com.sermon.mynote.domain.OrganizationUsers;

public interface OrganizationUsersService {

   public OrganizationUsers save(OrganizationUsers organizationUsers);
	

   
	public OrganizationUsers findById(Integer id);
	
	void delete(OrganizationUsers organizationUsers);
   
	public List<OrganizationUsers> findOrgUserById(int userid);
	
}

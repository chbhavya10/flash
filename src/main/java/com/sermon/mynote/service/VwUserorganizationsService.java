package com.sermon.mynote.service;

import java.util.List;

import com.sermon.mynote.domain.VwUserorganizations;


public interface VwUserorganizationsService {
	
	public List<VwUserorganizations> findOrganizationsByUser(int userid);

}

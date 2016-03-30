package com.sermon.mynote.service;

import java.util.List;


import com.sermon.mynote.domain.VwOrganizationNotes;

public interface VwOrganizationNotesService {
	
public List<VwOrganizationNotes> findAll();
	
public List<VwOrganizationNotes> findSermonsByOrgId(int orgid);

}

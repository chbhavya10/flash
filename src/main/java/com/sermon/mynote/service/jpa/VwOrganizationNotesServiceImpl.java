package com.sermon.mynote.service.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.sermon.mynote.domain.VwNoteSummary;
import com.sermon.mynote.domain.VwOrganizationNotes;
import com.sermon.mynote.repository.VwNoteSummaryRepository;
import com.sermon.mynote.repository.VwOrganizationNotesRepository;
import com.sermon.mynote.service.VwOrganizationNotesService;

@Service("vwOrganizationNotesService")
@Repository
@Transactional
public class VwOrganizationNotesServiceImpl implements VwOrganizationNotesService{
	
	
	@Autowired
	private VwOrganizationNotesRepository vwOrganizationNotesRepository;
	
	@Transactional(readOnly=true)
	public List<VwOrganizationNotes> findAll() {
		return Lists.newArrayList(vwOrganizationNotesRepository.findAll());
	}

	@Transactional(readOnly=true)
	public List<VwOrganizationNotes> findSermonsByOrgId(int orgid) {
		return Lists.newArrayList(vwOrganizationNotesRepository.findSermonsByOrgId(orgid));
	}

}

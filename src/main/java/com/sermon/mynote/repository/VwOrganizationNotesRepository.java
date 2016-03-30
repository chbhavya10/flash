package com.sermon.mynote.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sermon.mynote.domain.VwOrganizationNotes;

public interface VwOrganizationNotesRepository extends PagingAndSortingRepository<VwOrganizationNotes, Long>{

	public List<VwOrganizationNotes> findSermonsByOrgId(int orgid);

}

package com.sermon.mynote.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sermon.mynote.domain.SearchOrganization;



public interface VwSearchOrganizationRepository extends PagingAndSortingRepository<SearchOrganization, Long>{
	
	
	//public List<SearchOrganization> SearchOrganiz(String organizationName);

}
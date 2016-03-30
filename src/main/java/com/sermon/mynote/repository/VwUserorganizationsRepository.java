package com.sermon.mynote.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sermon.mynote.domain.VwUserorganizations;




public interface VwUserorganizationsRepository extends PagingAndSortingRepository<VwUserorganizations, Long>{
	
	public List<VwUserorganizations> findOrganizationsByUser(int userid);
		
	

}

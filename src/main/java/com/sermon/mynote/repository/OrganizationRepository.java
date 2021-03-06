package com.sermon.mynote.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sermon.mynote.domain.Organization;

public interface OrganizationRepository extends PagingAndSortingRepository<Organization, Integer> {

	public List<Organization> findChurchesByUser();

}

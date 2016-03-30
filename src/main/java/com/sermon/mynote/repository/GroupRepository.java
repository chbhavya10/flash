package com.sermon.mynote.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sermon.mynote.domain.OrganizationGroup;

public interface GroupRepository extends PagingAndSortingRepository<OrganizationGroup, Integer> {

}

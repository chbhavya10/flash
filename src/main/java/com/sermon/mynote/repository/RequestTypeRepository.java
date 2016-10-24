package com.sermon.mynote.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sermon.mynote.domain.RequestType;

public interface RequestTypeRepository extends PagingAndSortingRepository<RequestType, Integer> {

}

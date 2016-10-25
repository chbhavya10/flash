package com.sermon.mynote.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sermon.mynote.domain.Request;

public interface RequestRepository extends PagingAndSortingRepository<Request, Integer> {

}

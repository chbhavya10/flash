package com.sermon.mynote.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sermon.mynote.domain.EventType;

public interface EventTypeRepository extends PagingAndSortingRepository<EventType, Integer> {

}

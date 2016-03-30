package com.sermon.mynote.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sermon.mynote.domain.PublishSchedule;

public interface PublishRepository extends PagingAndSortingRepository<PublishSchedule, Integer> {

}
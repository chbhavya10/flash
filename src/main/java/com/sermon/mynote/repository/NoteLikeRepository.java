package com.sermon.mynote.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sermon.mynote.domain.NoteLike;

public interface NoteLikeRepository extends PagingAndSortingRepository<NoteLike, Integer> {

}

package com.sermon.mynote.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sermon.mynote.domain.Event;

public interface EventRepository extends PagingAndSortingRepository<Event, Integer> {

	// public List<Note> findNotesByAuthorId();

}

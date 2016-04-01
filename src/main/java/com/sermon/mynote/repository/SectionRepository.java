package com.sermon.mynote.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sermon.mynote.domain.Section;

public interface SectionRepository extends PagingAndSortingRepository<Section, Integer> {

	List<Section> findSectionByNoteId(Integer noteId);

}

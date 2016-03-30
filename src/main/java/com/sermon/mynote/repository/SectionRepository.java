package com.sermon.mynote.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sermon.mynote.domain.Section;



public interface SectionRepository extends PagingAndSortingRepository<Section, Integer>{

}

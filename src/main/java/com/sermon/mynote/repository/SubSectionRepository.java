package com.sermon.mynote.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sermon.mynote.domain.SubSection;

public interface SubSectionRepository extends PagingAndSortingRepository<SubSection, Integer> {

	List<SubSection> findSubsectionBySectionId(Integer sectionId);

}

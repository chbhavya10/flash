package com.sermon.mynote.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sermon.mynote.domain.VwNoteSummary;




public interface VwNoteSummaryRepository extends PagingAndSortingRepository<VwNoteSummary,Long>{
	
	public List<VwNoteSummary> findNoteSummaryByNoteId(int noteid);

}

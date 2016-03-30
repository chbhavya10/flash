package com.sermon.mynote.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sermon.mynote.domain.VwNotedetails;

public interface  VwNotedetailsRepository extends PagingAndSortingRepository<VwNotedetails,Long>{
	
	public List<VwNotedetails> findNoteDetailsByNoteId(int noteid);

}

package com.sermon.mynote.service.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.sermon.mynote.domain.VwNotedetails;
import com.sermon.mynote.repository.VwNotedetailsRepository;
import com.sermon.mynote.service.VwNotedetailsService;

@Service("vwNotedetailsService")
@Repository
@Transactional
public class VwNotedetailsServiceImpl implements VwNotedetailsService{

	
	@Autowired
	private VwNotedetailsRepository vwNotedetailsRepository;
	
	
	@Transactional(readOnly=true)
	public List<VwNotedetails> findNoteDetailsByNoteId(int noteid)
	{
		return Lists.newArrayList(vwNotedetailsRepository.findNoteDetailsByNoteId(noteid));
		
	}
	
}

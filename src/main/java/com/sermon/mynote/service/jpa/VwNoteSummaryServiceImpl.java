package com.sermon.mynote.service.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.sermon.mynote.domain.User;
import com.sermon.mynote.domain.VwNoteSummary;
import com.sermon.mynote.repository.VwNoteSummaryRepository;
import com.sermon.mynote.service.VwNoteSummaryService;

@Service("vwNoteSummaryService")
@Repository
@Transactional
public class VwNoteSummaryServiceImpl implements VwNoteSummaryService{

	@Autowired
	private VwNoteSummaryRepository vwNoteSummaryRepository;
	
	@Transactional(readOnly=true)
	public List<VwNoteSummary> findAll() {
		return Lists.newArrayList(vwNoteSummaryRepository.findAll());
	}

	@Transactional(readOnly=true)
	public List<VwNoteSummary> findNoteSummaryByNoteId(int noteid) {
		return Lists.newArrayList(vwNoteSummaryRepository.findNoteSummaryByNoteId(noteid));
	}
}

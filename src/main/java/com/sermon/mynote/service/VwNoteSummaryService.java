package com.sermon.mynote.service;

import java.util.List;


import com.sermon.mynote.domain.VwNoteSummary;


public interface VwNoteSummaryService {
	public List<VwNoteSummary> findNoteSummaryByNoteId(int noteid);
	
	public List<VwNoteSummary> findAll();

}

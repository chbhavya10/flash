package com.sermon.mynote.service;

import java.util.List;

import com.sermon.mynote.domain.VwNotedetails;

public interface VwNotedetailsService {
	public List<VwNotedetails> findNoteDetailsByNoteId(int noteid);

}

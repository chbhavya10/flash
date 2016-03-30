package com.sermon.mynote.service;


import com.sermon.mynote.domain.SubSection;

public interface SubSectionService {

public SubSection findById(Integer id);
	
	public SubSection save(SubSection subsection);
	
	public void delete(SubSection subsection);
}

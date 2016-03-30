package com.sermon.mynote.service;

import com.sermon.mynote.domain.Section;

public interface SectionService {

	public Section findById(Integer id);

	public Section save(Section section);

	public int delete(Section section);

}

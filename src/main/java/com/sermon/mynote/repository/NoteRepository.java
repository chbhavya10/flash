package com.sermon.mynote.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sermon.mynote.domain.Note;

public interface NoteRepository extends PagingAndSortingRepository<Note, Integer> {

	public List<Note> findNotesByAuthorId(int authorId);
}

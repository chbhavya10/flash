package com.sermon.mynote.service;

import java.util.List;

import com.sermon.mynote.domain.AddNote;
import com.sermon.mynote.domain.Note;
import com.sermon.mynote.domain.PublishSchedule;



public interface NoteService {
	
  //public List<Note> findAll();
	
	public Note findById(Integer id);
	
	public Note save(Note note);
	
	public int delete(Note note);
	
	public List<Note> findNotesByAuthorId(int id);
	
	public PublishSchedule publishLater(PublishSchedule publishSchedule);

	public int updatePublishNow(Integer noteId, String published);
	
	 public int createNote(AddNote note);
	
	/*
	public void createNote(Integer authorId,String title,String subTitle,String introduction,
			Date eventDate,Time eventTime,Integer noteCategoryId,String keywords,Integer organizationId);
	*/
	//public Page<Note> findAllByPage(Pageable pageable);	
	
   

}

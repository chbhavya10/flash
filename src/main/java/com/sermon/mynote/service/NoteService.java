package com.sermon.mynote.service;

import java.io.InputStream;
import java.util.List;

import com.amazonaws.services.s3.transfer.Upload;
import com.sermon.mynote.domain.AddNote;
import com.sermon.mynote.domain.Note;
import com.sermon.mynote.domain.NotePublish;
import com.sermon.mynote.domain.PublishSchedule;

public interface NoteService {

	// public List<Note> findAll();

	public Note findById(Integer id);

	public Note save(Note note);

	public int delete(Note note);

	public List<NotePublish> findNotesByAuthorId(int id);

	public PublishSchedule publishLater(PublishSchedule publishSchedule);

	public int updatePublishNow(Integer noteId, String published);

	public int createNote(AddNote note);

	public AddNote getNote(int id);

	public int updatePublish(PublishSchedule publishSchedule);

	public Upload upLoadNoteFiles(InputStream inputStream, String imgName, String imgToDelete, int noteId);
	
	public Upload upLoadEventFiles(InputStream inputStream, String imgName, String imgToDelete, int eventId);

	public String getNoteImage(int noteId);
	
	public String getEventImage(int eventImage);


	public int saveImage(int noteId, String imgName);
	
	public int saveEventImage(int noteId, String imgName);


	public InputStream getUserDocumentAsStream(int id);

	public Upload upLoadFiles(InputStream fis, String imageName, String imgToDelete, int noteId, int orgId);

	public int deleteImage(int noteId);

	public String generatePreSignedURL(String bucketName, String objectKey);

	/*
	 * public void createNote(Integer authorId,String title,String
	 * subTitle,String introduction, Date eventDate,Time eventTime,Integer
	 * noteCategoryId,String keywords,Integer organizationId);
	 */
	// public Page<Note> findAllByPage(Pageable pageable);

}

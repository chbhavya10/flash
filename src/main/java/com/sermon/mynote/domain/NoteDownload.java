package com.sermon.mynote.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
@Table(name = "NoteDownload")
public class NoteDownload implements Serializable {

	/**
  * 
  */
	private static final long serialVersionUID = 1L;
	private Integer noteDownloadKey;
	private int noteId;
	private int userId;
	private DateTime downloadDate;

	public NoteDownload() {

	}

	public NoteDownload(int noteId, int userId, DateTime downloadDate) {
		this.noteId = noteId;
		this.userId = userId;
		this.downloadDate = downloadDate;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "NoteDownloadKey")
	public Integer getNoteDownloadKey() {
		return noteDownloadKey;
	}

	public void setNoteDownloadKey(Integer noteDownloadKey) {
		this.noteDownloadKey = noteDownloadKey;
	}

	public int getNoteId() {
		return noteId;
	}

	public void setNoteId(int noteId) {
		this.noteId = noteId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	public DateTime getDownloadDate() {
		return downloadDate;
	}

	public void setDownloadDate(DateTime dateTime) {
		this.downloadDate = dateTime;
	}

}
package com.sermon.mynote.domain;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PublishSchedule")
public class PublishSchedule implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int publishScheduleId;
	private int noteId;
	private Date publishDate;
	private Time publishTime;

	public PublishSchedule() {
	}

	public PublishSchedule(int publishScheduleId, int noteId, Date publishDate, Time publishTime) {
		this.publishScheduleId = publishScheduleId;
		this.noteId = noteId;
		this.publishDate = publishDate;
		this.publishTime = publishTime;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PublishScheduleId")
	public int getPublishScheduleId() {
		return publishScheduleId;
	}

	public void setPublishScheduleId(int publishScheduleId) {
		this.publishScheduleId = publishScheduleId;
	}

	public int getNoteId() {
		return noteId;
	}

	public void setNoteId(int noteId) {
		this.noteId = noteId;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public Time getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Time publishTime) {
		this.publishTime = publishTime;
	}

}
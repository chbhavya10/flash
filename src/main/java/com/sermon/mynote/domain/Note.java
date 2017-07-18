package com.sermon.mynote.domain;

// Generated May 7, 2015 9:40:59 PM by Hibernate Tools 4.0.0

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Time;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

/**
 * Note generated by hbm2java
 */
@Entity
@NamedStoredProcedureQueries({

		@NamedStoredProcedureQuery(name = "Note.delete_note", procedureName = "delete_note", parameters = {
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "NoteId", type = Integer.class)

		}),

		@NamedStoredProcedureQuery(name = "Note.update_publish_now", procedureName = "update_publish_now", parameters = {
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "noteId", type = Integer.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "published", type = String.class) }) })
@Table(name = "note")
public class Note implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer noteId;
	private int authorId;
	private String title;
	private String subTitle;
	private String introduction;
	private Date eventDate;
	private Time eventTime;
	private Integer categoryId;
	private String keywords;
	private Integer OrganizationId;
	private Integer groupId;
	private String published;
	private String PreacherName;
	private String noteImage;
	
	
	public void setNoteId(int noteId) {
		this.noteId = noteId;
	}

	public String getNoteImage() {
		return noteImage;
	}

	public void setNoteImage(String noteImage) {
		this.noteImage = noteImage;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getPublished() {
		return published;
	}

	public String getPreacherName() {
		return PreacherName;
	}

	public void setPreacherName(String preacherName) {
		PreacherName = preacherName;
	}

	public void setPublished(String published) {
		this.published = published;
	}

	public Note() {
	}

	public Note(int authorId, String title) {
		this.authorId = authorId;
		this.title = title;
	}

	public Note(Integer noteId, int authorId, String title, String subTitle, String introduction, Date eventDate,
			Time eventTime, Integer categoryId, String keywords, Integer organizationId, Integer groupId,
			String published, String PreacherName, String noteImage) {
		this.noteId = noteId;
		this.authorId = authorId;
		this.title = title;
		this.subTitle = subTitle;
		this.introduction = introduction;
		this.eventDate = eventDate;
		this.eventTime = eventTime;
		this.categoryId = categoryId;
		this.keywords = keywords;
		this.OrganizationId = organizationId;
		this.groupId = groupId;
		this.published = published;
		this.PreacherName = PreacherName;
		this.noteImage = noteImage;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "NoteId")
	public Integer getNoteId() {
		return this.noteId;
	}

	public void setNoteId(Integer noteId) {
		this.noteId = noteId;
	}

	public int getAuthorId() {
		return this.authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubTitle() {
		return this.subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getIntroduction() {
		return this.introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public Date getEventDate() {
		return this.eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public Time getEventTime() {
		return this.eventTime;
	}

	public void setEventTime(Time eventTime) {
		this.eventTime = eventTime;
	}

	@Column(name = "NoteCategoryId")
	public Integer getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getKeywords() {
		return this.keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public Integer getOrganizationId() {
		return this.OrganizationId;
	}

	public void setOrganizationId(Integer organizationId) {
		this.OrganizationId = organizationId;
	}

}

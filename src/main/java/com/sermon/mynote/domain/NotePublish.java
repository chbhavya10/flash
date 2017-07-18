package com.sermon.mynote.domain;

import java.sql.Time;
import java.util.Date;

public class NotePublish {

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
    private int PublishScheduleId;
    private int NoteId;
    private String PublishDate;
    private String PublishTime;
    
	
    
	public int getAuthorId() {
		return authorId;
	}
	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubTitle() {
		return subTitle;
	}
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public Date getEventDate() {
		return eventDate;
	}
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}
	public Time getEventTime() {
		return eventTime;
	}
	public void setEventTime(Time eventTime) {
		this.eventTime = eventTime;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public Integer getOrganizationId() {
		return OrganizationId;
	}
	public void setOrganizationId(Integer organizationId) {
		OrganizationId = organizationId;
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
	public void setPublished(String published) {
		this.published = published;
	}
	public String getPreacherName() {
		return PreacherName;
	}
	public void setPreacherName(String preacherName) {
		PreacherName = preacherName;
	}
	public String getNoteImage() {
		return noteImage;
	}
	public void setNoteImage(String noteImage) {
		this.noteImage = noteImage;
	}
	public int getPublishScheduleId() {
		return PublishScheduleId;
	}
	public void setPublishScheduleId(int publishScheduleId) {
		PublishScheduleId = publishScheduleId;
	}
	public int getNoteId() {
		return NoteId;
	}
	public void setNoteId(int noteId) {
		NoteId = noteId;
	}
	public String getPublishDate() {
		return PublishDate;
	}
	public void setPublishDate(String publishDate) {
		PublishDate = publishDate;
	}
	public String getPublishTime() {
		return PublishTime;
	}
	public void setPublishTime(String publishTime) {
		PublishTime = publishTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
    
	
}

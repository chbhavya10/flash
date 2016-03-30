package com.sermon.mynote.domain;

import java.util.Date;
import java.util.List;

public class AddNote {

 private Integer noteId;
 private int authorId;
 private String title;
 private String subTitle;
 private String introduction;
 private Date eventDate;
 private Date eventTime;
 private Integer categoryId;
 private String keywords;
 private Integer OrganizationId;
 private Integer groupId;
 private String published;

 private List<Section> sections;

 private List<SubSection> subSections;

 public List<Section> getSections() {
  return sections;
 }

 public void setSections(List<Section> sections) {
  this.sections = sections;
 }

 public List<SubSection> getSubSections() {
  return subSections;
 }

 public void setSubSections(List<SubSection> subSections) {
  this.subSections = subSections;
 }

 public AddNote() {
 }

 public AddNote(Integer noteId, int authorId, String title, String subTitle, String introduction, Date eventDate,
   Date eventTime, Integer categoryId, String keywords, Integer organizationId, Integer groupId,
   String published, List<Section> sections, List<SubSection> subSections) {
  super();
  this.noteId = noteId;
  this.authorId = authorId;
  this.title = title;
  this.subTitle = subTitle;
  this.introduction = introduction;
  this.eventDate = eventDate;
  this.eventTime = eventTime;
  this.categoryId = categoryId;
  this.keywords = keywords;
  OrganizationId = organizationId;
  this.groupId = groupId;
  this.published = published;
  this.sections = sections;
  this.subSections = subSections;
 }

 public Integer getNoteId() {
  return noteId;
 }

 public void setNoteId(Integer noteId) {
  this.noteId = noteId;
 }

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

 public Date getEventTime() {
  return eventTime;
 }

 public void setEventTime(Date eventTime) {
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

}
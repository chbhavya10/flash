package com.sermon.mynote.domain;

import java.io.Serializable;
import java.util.List;

public class AddSection implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer sectionId;
	private int noteId;
	private String sectionText;
	private List<Integer> sectionKeyWords;

	public Integer getSectionId() {
		return sectionId;
	}

	public List<Integer> getSectionKeyWords() {
		return sectionKeyWords;
	}

	public void setSectionKeyWords(List<Integer> sectionKeyWords) {
		this.sectionKeyWords = sectionKeyWords;
	}

	public void setSectionId(Integer sectionId) {
		this.sectionId = sectionId;
	}

	public int getNoteId() {
		return noteId;
	}

	public void setNoteId(int noteId) {
		this.noteId = noteId;
	}

	public String getSectionText() {
		return sectionText;
	}

	public void setSectionText(String sectionText) {
		this.sectionText = sectionText;
	}

}

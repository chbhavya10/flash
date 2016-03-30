package com.sermon.mynote.domain;

import java.io.Serializable;
import java.util.List;

public class AddSubSection implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer subsectionId;
	private int sectionId;
	private String subsectionText;
	private List<Integer> subsectionKeyWords;

	public Integer getSubsectionId() {
		return subsectionId;
	}

	public void setSubsectionId(Integer subsectionId) {
		this.subsectionId = subsectionId;
	}

	public int getSectionId() {
		return sectionId;
	}

	public void setSectionId(int sectionId) {
		this.sectionId = sectionId;
	}

	public String getSubsectionText() {
		return subsectionText;
	}

	public void setSubsectionText(String subsectionText) {
		this.subsectionText = subsectionText;
	}

	public List<Integer> getSubsectionKeyWords() {
		return subsectionKeyWords;
	}

	public void setSubsectionKeyWords(List<Integer> subsectionKeyWords) {
		this.subsectionKeyWords = subsectionKeyWords;
	}

}

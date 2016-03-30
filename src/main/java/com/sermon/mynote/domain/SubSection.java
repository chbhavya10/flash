package com.sermon.mynote.domain;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "subsection")
public class SubSection implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6442222788889041904L;

	private Integer subsectionId;
	private int sectionId;
	private String subsectionText;
	private String subsectionKeyWords;
	
	
	public SubSection() {
	}

	public SubSection(int sectionId, String subsectionText, String subsectionKeyWords) {
		this.sectionId = sectionId;
		this.subsectionText = subsectionText;
		this.subsectionKeyWords = subsectionKeyWords;
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "SubSectionID")
	public Integer getSubsectionId() {
		return this.subsectionId;
	}
	public void setSubsectionId(Integer subsectionId) {
		this.subsectionId = subsectionId;
	}
	public int getSectionId() {
		return this.sectionId;
	}
	public void setSectionId(int sectionId) {
		this.sectionId = sectionId;
	}
	public String getSubsectionText() {
		return this.subsectionText;
	}
	public void setSubsectionText(String subsectionText) {
		this.subsectionText = subsectionText;
	}
	public String getSubsectionKeyWords() {
		return this.subsectionKeyWords;
	}
	public void setSubsectionKeyWords(String subsectionKeyWords) {
		this.subsectionKeyWords = subsectionKeyWords;
	}
	
	
	
	
}

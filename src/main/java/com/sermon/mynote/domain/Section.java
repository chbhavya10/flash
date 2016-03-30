package com.sermon.mynote.domain;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@NamedStoredProcedureQueries({

		@NamedStoredProcedureQuery(name = "Section.delete_section", procedureName = "delete_section", parameters = {

				@StoredProcedureParameter(mode = ParameterMode.IN, name = "SectionID", type = Integer.class) })

})

@Table(name = "section")
public class Section implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8315611465363412332L;
	private Integer sectionId;
	private int noteId;
	private String sectionText;
	private String sectionKeyWords;

	public Section() {
	}

	public Section(int noteId, String sectionText, String sectionKeyWords) {
		this.noteId = noteId;
		this.sectionText = sectionText;
		this.sectionKeyWords = sectionKeyWords;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "SectionId")
	public Integer getSectionId() {
		return this.sectionId;
	}

	public void setSectionId(Integer sectionId) {
		this.sectionId = sectionId;
	}

	public int getNoteId() {
		return this.noteId;
	}

	public void setNoteId(int noteId) {
		this.noteId = noteId;
	}

	public String getSectionText() {
		return this.sectionText;
	}

	public void setSectionText(String sectionText) {
		this.sectionText = sectionText;
	}

	public String getSectionKeyWords() {
		return this.sectionKeyWords;
	}

	public void setSectionKeyWords(String sectionKeyWords) {
		this.sectionKeyWords = sectionKeyWords;
	}

}

package com.sermon.mynote.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.sql.Timestamp;

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

		@NamedStoredProcedureQuery(name = "OrganizationFeedback.update_org_feedback", procedureName = "update_org_feedback", parameters = {
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "organizationId", type = Integer.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "userId", type = Integer.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "rating", type = Double.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "feedbackComments", type = String.class)

		}) })

@Table(name = "OrganizationFeedback")
public class OrganizationFeedback implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int organizationFeedbackId;
	private int organizationId;
	private int userId;
	private double rating;
	private String feedbackComments;
	private Timestamp feedbackDate;

	public OrganizationFeedback() {
	}

	public OrganizationFeedback(int organizationFeedbackId, int organizationId, int userId, double rating,
			String feedbackComments, Timestamp feedbackDate) {
		this.organizationFeedbackId = organizationFeedbackId;
		this.organizationId = organizationId;
		this.userId = userId;
		this.rating = rating;
		this.feedbackComments = feedbackComments;
		this.feedbackDate = feedbackDate;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "organizationFeedbackId")
	public int getOrganizationFeedbackId() {
		return organizationFeedbackId;
	}

	public void setOrganizationFeedbackId(int organizationFeedbackId) {
		this.organizationFeedbackId = organizationFeedbackId;
	}

	public int getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public String getFeedbackComments() {
		return feedbackComments;
	}

	public void setFeedbackComments(String feedbackComments) {
		this.feedbackComments = feedbackComments;
	}

	public Timestamp getFeedbackDate() {
		return feedbackDate;
	}

	public void setFeedbackDate(Timestamp feedbackDate) {
		this.feedbackDate = feedbackDate;
	}

}

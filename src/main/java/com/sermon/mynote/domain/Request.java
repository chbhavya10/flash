package com.sermon.mynote.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
@NamedStoredProcedureQueries({

		@NamedStoredProcedureQuery(name = "Request.update_request", procedureName = "update_request", parameters = {
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "requestId", type = Integer.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "requestUpdate", type = String.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "requestStatus", type = String.class)

		}) })
@Table(name = "Request")
public class Request implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int requestId;
	private int requestTypeId;
	private DateTime requestDate;
	private int userId;
	private int organizationId;
	private String requestNotes;
	private String requestUpdate;
	private String requestStatus;
	private String isPrivate;
	private String requestEmail;
	private String requestPhone;

	public Request() {
	}

	public Request(int requestTypeId, DateTime requestDate, int userId, int organizationId, String requestNotes,
			String requestUpdate, String requestStatus, String isPrivate, String requestEmail, String requestPhone) {
		this.requestTypeId = requestTypeId;
		this.requestDate = requestDate;
		this.userId = userId;
		this.organizationId = organizationId;
		this.requestNotes = requestNotes;
		this.requestUpdate = requestUpdate;
		this.requestStatus = requestStatus;
		this.isPrivate = isPrivate;
		this.requestEmail = requestEmail;
		this.requestPhone = requestPhone;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "RequestId")
	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	public int getRequestTypeId() {
		return requestTypeId;
	}

	public void setRequestTypeId(int requestTypeId) {
		this.requestTypeId = requestTypeId;
	}

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	public DateTime getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(DateTime requestDate) {
		this.requestDate = requestDate;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}

	public String getRequestNotes() {
		return requestNotes;
	}

	public void setRequestNotes(String requestNotes) {
		this.requestNotes = requestNotes;
	}

	public String getRequestUpdate() {
		return requestUpdate;
	}

	public void setRequestUpdate(String requestUpdate) {
		this.requestUpdate = requestUpdate;
	}

	public String getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}

	public String getIsPrivate() {
		return isPrivate;
	}

	public void setIsPrivate(String isPrivate) {
		this.isPrivate = isPrivate;
	}

	public String getRequestEmail() {
		return requestEmail;
	}

	public void setRequestEmail(String requestEmail) {
		this.requestEmail = requestEmail;
	}

	public String getRequestPhone() {
		return requestPhone;
	}

	public void setRequestPhone(String requestPhone) {
		this.requestPhone = requestPhone;
	}

}

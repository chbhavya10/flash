package com.sermon.mynote.domain;

import java.io.Serializable;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

public class RequestDetails implements Serializable {

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
	private String requestedBy;
	private String requestType;
	private String userEmail;
	private String userPhone;
	private String organizationName;

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

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

	public String getRequestedBy() {
		return requestedBy;
	}

	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

}

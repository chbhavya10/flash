package com.sermon.mynote.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
@NamedQueries({
		@NamedQuery(name = "VwUserRequests.findRequestsByUserId", query = "SELECT o FROM VwUserRequests o  WHERE o.userId = (?1)"),
		@NamedQuery(name = "VwUserRequests.findRequestsByOrgId", query = "SELECT o FROM VwUserRequests o  WHERE o.organizationId = (?1)") })
@Table(name = "vw_UserRequests")
public class VwUserRequests implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int requestId;
	private int requestTypeId;
	private DateTime requestDate;
	private String requestStatus;
	private String requestType;
	private int organizationId;
	private String organizationName;
	private int userId;
	private String isPrivate;
	private String requestEmail;
	private String requestPhone;
	private String requestedBy;
	private String userEmail;
	private String userPhone;

	public VwUserRequests() {
	}

	public VwUserRequests(int requestTypeId, DateTime requestDate, String requestStatus, String requestType,
			int organizationId, String organizationName, int userId, String isPrivate, String requestEmail,
			String requestPhone, String requestedBy, String userEmail, String userPhone) {

		this.requestTypeId = requestTypeId;
		this.requestDate = requestDate;
		this.requestStatus = requestStatus;
		this.requestType = requestType;
		this.organizationId = organizationId;
		this.organizationName = organizationName;
		this.userId = userId;
		this.isPrivate = isPrivate;
		this.requestEmail = requestEmail;
		this.requestPhone = requestPhone;
		this.requestedBy = requestedBy;
		this.userEmail = userEmail;
		this.userPhone = userPhone;
	}

	@Id
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

	public String getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public int getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof VwUserRequests))
			return false;
		VwUserRequests castOther = (VwUserRequests) other;

		return (this.getRequestId() == castOther.getRequestId())
				&& (this.getOrganizationId() == castOther.getOrganizationId())
				&& (this.getRequestTypeId() == castOther.getRequestTypeId())
				&& (this.getUserId() == castOther.getUserId()) && (this.getIsPrivate() == castOther.getIsPrivate())
				&& ((this.getOrganizationName() == castOther.getOrganizationName())
						|| (this.getOrganizationName() != null && castOther.getOrganizationName() != null
								&& this.getOrganizationName().equals(castOther.getOrganizationName())))
				&& ((this.getRequestDate() == castOther.getRequestDate())
						|| (this.getRequestDate() != null && castOther.getRequestDate() != null
								&& this.getRequestDate().equals(castOther.getRequestDate())))
				&& ((this.getRequestStatus() == castOther.getRequestStatus())
						|| (this.getRequestStatus() != null && castOther.getRequestStatus() != null
								&& this.getRequestStatus().equals(castOther.getRequestStatus())))
				&& ((this.getRequestType() == castOther.getRequestType())
						|| (this.getRequestType() != null && castOther.getRequestType() != null
								&& this.getRequestType().equals(castOther.getRequestType())))
				&& ((this.getRequestEmail() == castOther.getRequestEmail())
						|| (this.getRequestEmail() != null && castOther.getRequestEmail() != null
								&& this.getRequestEmail().equals(castOther.getRequestEmail())))
				&& ((this.getRequestPhone() == castOther.getRequestPhone())
						|| (this.getRequestPhone() != null && castOther.getRequestPhone() != null
								&& this.getRequestPhone().equals(castOther.getRequestPhone())))
				&& ((this.getRequestedBy() == castOther.getRequestedBy())
						|| (this.getRequestedBy() != null && castOther.getRequestedBy() != null
								&& this.getRequestedBy().equals(castOther.getRequestedBy())))
				&& ((this.getUserEmail() == castOther.getUserEmail()) || (this.getUserEmail() != null
						&& castOther.getUserEmail() != null && this.getUserEmail().equals(castOther.getUserEmail())))
				&& ((this.getUserPhone() == castOther.getUserPhone()) || (this.getUserPhone() != null
						&& castOther.getUserPhone() != null && this.getUserPhone().equals(castOther.getUserPhone())));

	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getRequestId();
		result = 37 * result + this.getOrganizationId();
		result = 37 * result + this.getRequestTypeId();
		result = 37 * result + this.getUserId();
		result = 37 * result + (getIsPrivate() == null ? 0 : this.getIsPrivate().hashCode());
		result = 37 * result + (getOrganizationName() == null ? 0 : this.getOrganizationName().hashCode());
		result = 37 * result + (getRequestDate() == null ? 0 : this.getRequestDate().hashCode());
		result = 37 * result + (getRequestStatus() == null ? 0 : this.getRequestStatus().hashCode());
		result = 37 * result + (getRequestType() == null ? 0 : this.getRequestType().hashCode());
		result = 37 * result + (getRequestEmail() == null ? 0 : this.getRequestEmail().hashCode());
		result = 37 * result + (getRequestPhone() == null ? 0 : this.getRequestPhone().hashCode());
		result = 37 * result + (getRequestedBy() == null ? 0 : this.getRequestedBy().hashCode());
		result = 37 * result + (getUserEmail() == null ? 0 : this.getUserEmail().hashCode());
		result = 37 * result + (getUserPhone() == null ? 0 : this.getUserPhone().hashCode());

		return result;
	}

}

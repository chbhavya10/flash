package com.sermon.mynote.domain;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@NamedStoredProcedureQuery(name = "OrganizationInfo.update_orgInfo", procedureName = "update_orgInfo", parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "organizationId", type = Integer.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "website", type = String.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "primaryEmail", type = String.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "generalInfo", type = String.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "hours", type = String.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "facebookLink", type = String.class) })

@Table(name = "OrganizationInfo")
public class OrganizationInfo {

	private int organizationInfoId;
	private int organizationId;
	private String website;
	private String phone;
	private String primaryEmail;
	private String secondaryEmail;
	private String generalInfo;
	private String latitude;
	private String longitude;
	private String hours;
	private String facebookLink;

	public OrganizationInfo() {
	}

	public OrganizationInfo(int organizationId, String website, String phone, String primaryEmail,
			String secondaryEmail, String generalInfo, String latitude, String longitude, String hours,
			String facebookLink) {
		this.organizationId = organizationId;
		this.website = website;
		this.phone = phone;
		this.primaryEmail = primaryEmail;
		this.secondaryEmail = secondaryEmail;
		this.generalInfo = generalInfo;
		this.latitude = latitude;
		this.longitude = longitude;
		this.hours = hours;
		this.facebookLink = facebookLink;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "OrganizationInfoId")
	public int getOrganizationInfoId() {
		return organizationInfoId;
	}

	public void setOrganizationInfoId(int organizationInfoId) {
		this.organizationInfoId = organizationInfoId;
	}

	public int getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPrimaryEmail() {
		return primaryEmail;
	}

	public void setPrimaryEmail(String primaryEmail) {
		this.primaryEmail = primaryEmail;
	}

	public String getSecondaryEmail() {
		return secondaryEmail;
	}

	public void setSecondaryEmail(String secondaryEmail) {
		this.secondaryEmail = secondaryEmail;
	}

	public String getGeneralInfo() {
		return generalInfo;
	}

	public void setGeneralInfo(String generalInfo) {
		this.generalInfo = generalInfo;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}

	public String getFacebookLink() {
		return facebookLink;
	}

	public void setFacebookLink(String facebookLink) {
		this.facebookLink = facebookLink;
	}

}

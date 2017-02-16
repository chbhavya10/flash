package com.sermon.mynote.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vw_organizationInfo")
public class VwOrganizationInfo {

	private int OrganizationId;
	private String OrganizationName;
	private String Address1;
	private String Address2;
	private int CityId;
	private String CityName;
	private int StateId;
	private String StateName;
	private int CountryID;
	private String CountryName;
	private String ZipCode;
	private String Hours;
	private String GeneralInfo;
	private String Website;
	private String PrimaryEmail;
	private String FacebookLink;
	private String OrgImage;

	public VwOrganizationInfo() {
	}

	public VwOrganizationInfo(int organizationId, String organizationName, String address1, String address2, int cityId,
			String cityName, int stateId, String stateName, int countryID, String countryName, String zipCode,
			String hours, String generalInfo, String website, String primaryEmail, String facebookLink,
			String orgImage) {
		OrganizationId = organizationId;
		OrganizationName = organizationName;
		Address1 = address1;
		Address2 = address2;
		CityId = cityId;
		CityName = cityName;
		StateId = stateId;
		StateName = stateName;
		CountryID = countryID;
		CountryName = countryName;
		ZipCode = zipCode;
		Hours = hours;
		GeneralInfo = generalInfo;
		Website = website;
		PrimaryEmail = primaryEmail;
		FacebookLink = facebookLink;
		OrgImage = orgImage;
	}

	@Id
	@Column(name = "OrganizationId")
	public int getOrganizationId() {
		return OrganizationId;
	}

	public void setOrganizationId(int organizationId) {
		OrganizationId = organizationId;
	}

	public String getOrganizationName() {
		return OrganizationName;
	}

	public void setOrganizationName(String organizationName) {
		OrganizationName = organizationName;
	}

	public String getAddress1() {
		return Address1;
	}

	public void setAddress1(String address1) {
		Address1 = address1;
	}

	public String getAddress2() {
		return Address2;
	}

	public void setAddress2(String address2) {
		Address2 = address2;
	}

	public int getCityId() {
		return CityId;
	}

	public void setCityId(int cityId) {
		CityId = cityId;
	}

	public String getCityName() {
		return CityName;
	}

	public void setCityName(String cityName) {
		CityName = cityName;
	}

	public int getStateId() {
		return StateId;
	}

	public void setStateId(int stateId) {
		StateId = stateId;
	}

	public String getStateName() {
		return StateName;
	}

	public void setStateName(String stateName) {
		StateName = stateName;
	}

	public int getCountryID() {
		return CountryID;
	}

	public void setCountryID(int countryID) {
		CountryID = countryID;
	}

	public String getCountryName() {
		return CountryName;
	}

	public void setCountryName(String countryName) {
		CountryName = countryName;
	}

	public String getZipCode() {
		return ZipCode;
	}

	public void setZipCode(String zipCode) {
		ZipCode = zipCode;
	}

	public String getHours() {
		return Hours;
	}

	public void setHours(String hours) {
		Hours = hours;
	}

	public String getGeneralInfo() {
		return GeneralInfo;
	}

	public void setGeneralInfo(String generalInfo) {
		GeneralInfo = generalInfo;
	}

	public String getWebsite() {
		return Website;
	}

	public void setWebsite(String website) {
		Website = website;
	}

	public String getPrimaryEmail() {
		return PrimaryEmail;
	}

	public void setPrimaryEmail(String primaryEmail) {
		PrimaryEmail = primaryEmail;
	}

	public String getFacebookLink() {
		return FacebookLink;
	}

	public void setFacebookLink(String facebookLink) {
		FacebookLink = facebookLink;
	}

	public String getOrgImage() {
		return OrgImage;
	}

	public void setOrgImage(String orgImage) {
		OrgImage = orgImage;
	}

}

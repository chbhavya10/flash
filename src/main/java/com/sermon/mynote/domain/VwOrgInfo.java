package com.sermon.mynote.domain;

import java.io.Serializable;

public class VwOrgInfo implements  Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	private String Denomination;
	private String Pastor1Bio;
	private String Pastor2Bio;
	private String Phone;
	private int likeCount;
	private int downloadCount;
	private int sermonCount;
	private String stripeAcctId;
	
	

	/**
	 * @return the organizationId
	 */
	public int getOrganizationId() {
		return OrganizationId;
	}

	/**
	 * @param organizationId
	 *            the organizationId to set
	 */
	public void setOrganizationId(int organizationId) {
		OrganizationId = organizationId;
	}

	/**
	 * @return the organizationName
	 */
	public String getOrganizationName() {
		return OrganizationName;
	}

	/**
	 * @param organizationName
	 *            the organizationName to set
	 */
	public void setOrganizationName(String organizationName) {
		OrganizationName = organizationName;
	}

	/**
	 * @return the address1
	 */
	public String getAddress1() {
		return Address1;
	}

	/**
	 * @param address1
	 *            the address1 to set
	 */
	public void setAddress1(String address1) {
		Address1 = address1;
	}

	/**
	 * @return the address2
	 */
	public String getAddress2() {
		return Address2;
	}

	/**
	 * @param address2
	 *            the address2 to set
	 */
	public void setAddress2(String address2) {
		Address2 = address2;
	}

	/**
	 * @return the cityId
	 */
	public int getCityId() {
		return CityId;
	}

	/**
	 * @param cityId
	 *            the cityId to set
	 */
	public void setCityId(int cityId) {
		CityId = cityId;
	}

	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return CityName;
	}

	/**
	 * @param cityName
	 *            the cityName to set
	 */
	public void setCityName(String cityName) {
		CityName = cityName;
	}

	/**
	 * @return the stateId
	 */
	public int getStateId() {
		return StateId;
	}

	/**
	 * @param stateId
	 *            the stateId to set
	 */
	public void setStateId(int stateId) {
		StateId = stateId;
	}

	/**
	 * @return the stateName
	 */
	public String getStateName() {
		return StateName;
	}

	/**
	 * @param stateName
	 *            the stateName to set
	 */
	public void setStateName(String stateName) {
		StateName = stateName;
	}

	/**
	 * @return the countryID
	 */
	public int getCountryID() {
		return CountryID;
	}

	/**
	 * @param countryID
	 *            the countryID to set
	 */
	public void setCountryID(int countryID) {
		CountryID = countryID;
	}

	/**
	 * @return the countryName
	 */
	public String getCountryName() {
		return CountryName;
	}

	/**
	 * @param countryName
	 *            the countryName to set
	 */
	public void setCountryName(String countryName) {
		CountryName = countryName;
	}

	/**
	 * @return the zipCode
	 */
	public String getZipCode() {
		return ZipCode;
	}

	/**
	 * @param zipCode
	 *            the zipCode to set
	 */
	public void setZipCode(String zipCode) {
		ZipCode = zipCode;
	}

	/**
	 * @return the hours
	 */
	public String getHours() {
		return Hours;
	}

	/**
	 * @param hours
	 *            the hours to set
	 */
	public void setHours(String hours) {
		Hours = hours;
	}

	/**
	 * @return the generalInfo
	 */
	public String getGeneralInfo() {
		return GeneralInfo;
	}

	/**
	 * @param generalInfo
	 *            the generalInfo to set
	 */
	public void setGeneralInfo(String generalInfo) {
		GeneralInfo = generalInfo;
	}

	/**
	 * @return the website
	 */
	public String getWebsite() {
		return Website;
	}

	/**
	 * @param website
	 *            the website to set
	 */
	public void setWebsite(String website) {
		Website = website;
	}

	/**
	 * @return the primaryEmail
	 */
	public String getPrimaryEmail() {
		return PrimaryEmail;
	}

	/**
	 * @param primaryEmail
	 *            the primaryEmail to set
	 */
	public void setPrimaryEmail(String primaryEmail) {
		PrimaryEmail = primaryEmail;
	}

	/**
	 * @return the facebookLink
	 */
	public String getFacebookLink() {
		return FacebookLink;
	}

	/**
	 * @param facebookLink
	 *            the facebookLink to set
	 */
	public void setFacebookLink(String facebookLink) {
		FacebookLink = facebookLink;
	}

	/**
	 * @return the orgImage
	 */
	public String getOrgImage() {
		return OrgImage;
	}

	/**
	 * @param orgImage
	 *            the orgImage to set
	 */
	public void setOrgImage(String orgImage) {
		OrgImage = orgImage;
	}

	/**
	 * @return the denomination
	 */
	public String getDenomination() {
		return Denomination;
	}

	/**
	 * @param denomination
	 *            the denomination to set
	 */
	public void setDenomination(String denomination) {
		Denomination = denomination;
	}

	/**
	 * @return the pastor1Bio
	 */
	public String getPastor1Bio() {
		return Pastor1Bio;
	}

	/**
	 * @param pastor1Bio
	 *            the pastor1Bio to set
	 */
	public void setPastor1Bio(String pastor1Bio) {
		Pastor1Bio = pastor1Bio;
	}

	/**
	 * @return the pastor2Bio
	 */
	public String getPastor2Bio() {
		return Pastor2Bio;
	}

	/**
	 * @param pastor2Bio
	 *            the pastor2Bio to set
	 */
	public void setPastor2Bio(String pastor2Bio) {
		Pastor2Bio = pastor2Bio;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return Phone;
	}

	/**
	 * @param phone
	 *            the phone to set
	 */
	public void setPhone(String phone) {
		Phone = phone;
	}

	/**
	 * @return the likeCount
	 */
	public int getLikeCount() {
		return likeCount;
	}

	/**
	 * @param likeCount
	 *            the likeCount to set
	 */
	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	/**
	 * @return the downloadCount
	 */
	public int getDownloadCount() {
		return downloadCount;
	}

	/**
	 * @param downloadCount
	 *            the downloadCount to set
	 */
	public void setDownloadCount(int downloadCount) {
		this.downloadCount = downloadCount;
	}

	/**
	 * @return the sermonCount
	 */
	public int getSermonCount() {
		return sermonCount;
	}

	/**
	 * @param sermonCount
	 *            the sermonCount to set
	 */
	public void setSermonCount(int sermonCount) {
		this.sermonCount = sermonCount;
	}

	public String getStripeAcctId() {
		return stripeAcctId;
	}

	public void setStripeAcctId(String stripeAcctId) {
		this.stripeAcctId = stripeAcctId;
	}

}

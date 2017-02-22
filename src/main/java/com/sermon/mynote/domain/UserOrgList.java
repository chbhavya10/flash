package com.sermon.mynote.domain;

import java.io.Serializable;

public class UserOrgList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int orgUserId;
	private int organizationId;
	private int userId;
	private String userName;
	private String organizationName;
	private String cityName;
	private String stateName;
	private String countryName;
	private String zipCode;
	private int likeCount;
	private int downloadCount;
	private int sermonCount;
	private String orgImage;

	public String getOrgImage() {
		return orgImage;
	}

	public void setOrgImage(String orgImage) {
		this.orgImage = orgImage;
	}

	public int getOrgUserId() {
		return orgUserId;
	}

	public void setOrgUserId(int orgUserId) {
		this.orgUserId = orgUserId;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	public int getDownloadCount() {
		return downloadCount;
	}

	public void setDownloadCount(int downloadCount) {
		this.downloadCount = downloadCount;
	}

	public int getSermonCount() {
		return sermonCount;
	}

	public void setSermonCount(int sermonCount) {
		this.sermonCount = sermonCount;
	}

}

package com.sermon.mynote.domain;

import java.io.Serializable;

public class OrganizationLike implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer organizationId;
	private String organizationName;
	private String address1;
	private String address2;
	private String cityName;
	private String stateName;
	private String countryName;
	private String zipcode;
	private int likeCount;
	private int downloadCount;
	private int sermonCount;
	private String denomination;
	private String orgImage;

	public String getOrgImage() {
		return orgImage;
	}

	public void setOrgImage(String orgImage) {
		this.orgImage = orgImage;
	}

	public String getDenomination() {
		return denomination;
	}

	public void setDenomination(String denomination) {
		this.denomination = denomination;
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

	public Integer getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
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

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

}

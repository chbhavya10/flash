package com.sermon.mynote.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vw_organization")
public class SearchOrg implements Serializable{

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
	private String denomination;

	public SearchOrg() {
	}

	public SearchOrg(Integer organizationId, String organizationName, String address1, String address2, String cityName,
			String stateName, String countryName, String zipcode, String denomination) {
		this.organizationId = organizationId;
		this.organizationName = organizationName;
		this.address1 = address1;
		this.address2 = address2;
		this.cityName = cityName;
		this.stateName = stateName;
		this.countryName = countryName;
		this.zipcode = zipcode;
		this.denomination = denomination;

	}

	@Id
	@Column(name = "OrganizationId")
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

	public String getDenomination() {
		return denomination;
	}

	public void setDenomination(String denomination) {
		this.denomination = denomination;
	}

}

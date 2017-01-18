package com.sermon.mynote.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.OneToMany;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

// Generated May 7, 2015 9:40:59 PM by Hibernate Tools 4.0.0

/**
 * Organization generated by hbm2java
 */
@Entity
@NamedQueries({ @NamedQuery(name = "Organization.findChurchesByUser", query = "SELECT o FROM Organization o ") })
@NamedStoredProcedureQueries({

		@NamedStoredProcedureQuery(name = "Organization.update_organization", procedureName = "update_organization", parameters = {
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "organizationId", type = Integer.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "address1", type = String.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "address2", type = String.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "cityId", type = Integer.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "stateId", type = Integer.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "countryId", type = Integer.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "zipCode", type = String.class) }) })
@Table(name = "organization")
public class Organization implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1277403215095879584L;
	private Integer organizationId;
	private String organizationName;
	private Integer organizationTypeId;
	private String status;
	private String address1;
	private String address2;
	private Integer cityId;
	private Integer stateId;
	private Integer countryId;
	private String zipCode;

	public Organization() {
	}

	public Organization(String organizationName, Integer organizationTypeId, String status, String address1,
			String address2, Integer cityId, Integer stateId, Integer countryId, String zipCode) {
		this.organizationName = organizationName;
		this.organizationTypeId = organizationTypeId;
		this.status = status;
		this.address1 = address1;
		this.address2 = address2;
		this.cityId = cityId;
		this.stateId = stateId;
		this.countryId = countryId;
		this.zipCode = zipCode;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "OrganizationId")
	public Integer getOrganizationId() {
		return this.organizationId;
	}

	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}

	private List<OrganizationUsers> organizationUsers;
	/*
	 * private City city; private State state; private Country country;
	 */

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = OrganizationUsers.class)
	@JoinColumn(name = "OrganizationId")
	public List<OrganizationUsers> getOrganizationUsers() {
		return organizationUsers;
	}

	public void setOrganizationUsers(List<OrganizationUsers> organizationUsers) {
		this.organizationUsers = organizationUsers;
	}

	public String getOrganizationName() {
		return this.organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public Integer getOrganizationTypeId() {
		return this.organizationTypeId;
	}

	public void setOrganizationTypeId(Integer organizationTypeId) {
		this.organizationTypeId = organizationTypeId;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAddress1() {
		return this.address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return this.address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	/*
	 * @ManyToOne(cascade=CascadeType.ALL,fetch = FetchType.EAGER, targetEntity
	 * = City.class)
	 * 
	 * @JoinColumn(name="CityID") public City getCity() { return city; }
	 * 
	 * public void setCity(City city) { this.city = city; }
	 * 
	 */

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Integer getStateId() {
		return this.stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	public Integer getCountryId() {
		return this.countryId;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

	public String getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/*
	 * @ManyToOne(cascade=CascadeType.ALL,fetch = FetchType.EAGER, targetEntity
	 * = State.class)
	 * 
	 * @JoinColumn(name="StateId") public State getState() { return state; }
	 * 
	 * public void setState(State state) { this.state = state; }
	 * 
	 * @ManyToOne(cascade=CascadeType.ALL,fetch = FetchType.EAGER, targetEntity
	 * = Country.class)
	 * 
	 * @JoinColumn(name="CountryID") public Country getCountry() { return
	 * country; }
	 * 
	 * public void setCountry(Country country) { this.country = country; }
	 */

}

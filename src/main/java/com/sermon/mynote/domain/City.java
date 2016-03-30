package com.sermon.mynote.domain;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

// Generated May 7, 2015 9:40:59 PM by Hibernate Tools 4.0.0

/**
 * City generated by hbm2java
 */
@Entity
@Table(name = "city")
public class City implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer cityId;
	private String cityName;
	private Integer stateId;

	public City() {
	}

	public City(String cityName) {
		this.cityName = cityName;
	}

	public City(String cityName, Integer stateId) {
		this.cityName = cityName;
		this.stateId = stateId;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "CityId")
	public Integer getCityId() {
		return this.cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return this.cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Integer getStateId() {
		return this.stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

}

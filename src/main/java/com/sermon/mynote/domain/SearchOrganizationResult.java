package com.sermon.mynote.domain;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.Set;

public class SearchOrganizationResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigInteger totalCount;
	private List<OrganizationLike> result;
	private Set<Country> countries;
	private Set<State> states;
	private Set<City> cities;
	private Set<Denomination> denominations;

	public Set<Country> getCountries() {
		return countries;
	}

	public void setCountries(Set<Country> countries) {
		this.countries = countries;
	}

	public Set<State> getStates() {
		return states;
	}

	public void setStates(Set<State> states) {
		this.states = states;
	}

	public Set<City> getCities() {
		return cities;
	}

	public void setCities(Set<City> cities) {
		this.cities = cities;
	}

	public Set<Denomination> getDenominations() {
		return denominations;
	}

	public void setDenominations(Set<Denomination> denominations) {
		this.denominations = denominations;
	}

	public BigInteger getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(BigInteger totalCount) {
		this.totalCount = totalCount;
	}

	public List<OrganizationLike> getResult() {
		return result;
	}

	public void setResult(List<OrganizationLike> result) {
		this.result = result;
	}

}

package com.sermon.mynote.service;

import java.util.List;

import com.sermon.mynote.domain.City;
import com.sermon.mynote.domain.Country;
import com.sermon.mynote.domain.State;

public interface StatesService {

	public List<State> findStateByCountryId(Integer countryId);

	public List<Country> findAll();

	public List<City> findCityByStateId(Integer stateId);

	public List<Country> findCountryIdByCountryName(String countryName);

	public List<State> findStateIdByStateName(String stateName);

}

package com.sermon.mynote.service.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.sermon.mynote.domain.City;
import com.sermon.mynote.domain.Country;
import com.sermon.mynote.domain.State;
import com.sermon.mynote.repository.CityRepository;
import com.sermon.mynote.repository.CountryRepository;
import com.sermon.mynote.repository.StateRepository;
import com.sermon.mynote.service.StatesService;

@Service("statesService")
@Repository
@Transactional
public class StatesServiceImpl implements StatesService{
	
	@PersistenceContext
    private EntityManager em;
	
	@Autowired
	private StateRepository stateRepository;
	
	@Autowired
	private CountryRepository countryRepository;
	
	@Autowired
	private CityRepository cityRepository;
	

	@Transactional(readOnly=true)
	public List<State> findStateByCountryId(Integer countryId) {
		return Lists.newArrayList(stateRepository.findStateByCountryId(countryId));
	}

	@Transactional(readOnly=true)
	public List<Country> findAll() {
		return Lists.newArrayList(countryRepository.findAll());
	}

	@Override
	public List<City> findCityByStateId(Integer stateId) {
		return Lists.newArrayList(cityRepository.findCityByStateId(stateId));
	}

}

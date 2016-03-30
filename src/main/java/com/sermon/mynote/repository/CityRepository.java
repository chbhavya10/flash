package com.sermon.mynote.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sermon.mynote.domain.City;

public interface CityRepository extends PagingAndSortingRepository<City, Integer> {
	
	List<City> findCityByStateId(Integer stateId);

}

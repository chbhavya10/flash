package com.sermon.mynote.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sermon.mynote.domain.State;

public interface StateRepository extends PagingAndSortingRepository<State, Integer>{

	List<State> findStateByCountryId(Integer countryId);

}

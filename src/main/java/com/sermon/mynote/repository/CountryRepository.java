package com.sermon.mynote.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sermon.mynote.domain.Country;

public interface CountryRepository extends PagingAndSortingRepository<Country, Integer> {

	List<Country> findCountryIdByCountryName(String countryName);

}

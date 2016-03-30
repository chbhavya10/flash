package com.sermon.mynote.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sermon.mynote.domain.Country;

public interface CountryRepository extends PagingAndSortingRepository<Country, Integer> {

}

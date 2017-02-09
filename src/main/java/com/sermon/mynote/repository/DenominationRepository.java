package com.sermon.mynote.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sermon.mynote.domain.Denomination;

public interface DenominationRepository extends PagingAndSortingRepository<Denomination, Integer> {

}

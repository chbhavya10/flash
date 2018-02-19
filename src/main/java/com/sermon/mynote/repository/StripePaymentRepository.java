package com.sermon.mynote.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sermon.mynote.domain.Donation;

public interface StripePaymentRepository extends PagingAndSortingRepository<Donation,Integer>{
   
	 
}

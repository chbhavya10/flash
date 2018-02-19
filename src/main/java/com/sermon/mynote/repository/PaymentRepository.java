package com.sermon.mynote.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sermon.mynote.domain.Payment;

public interface PaymentRepository extends PagingAndSortingRepository<Payment, Integer>{

}

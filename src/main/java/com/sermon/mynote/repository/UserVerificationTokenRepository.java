package com.sermon.mynote.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sermon.mynote.domain.UserVerificationTokens;

public interface UserVerificationTokenRepository extends PagingAndSortingRepository<UserVerificationTokens, Integer> {

}

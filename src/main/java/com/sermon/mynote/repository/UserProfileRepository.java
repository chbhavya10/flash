package com.sermon.mynote.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sermon.mynote.domain.UserProfile;

public interface UserProfileRepository extends PagingAndSortingRepository<UserProfile, Integer>{

}

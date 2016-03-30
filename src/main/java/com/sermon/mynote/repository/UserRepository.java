package com.sermon.mynote.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sermon.mynote.domain.User;


public interface UserRepository extends PagingAndSortingRepository<User, Integer>{

}

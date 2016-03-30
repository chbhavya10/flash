package com.sermon.mynote.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sermon.mynote.domain.UserFavorite;

public interface UserFavoriteRepository extends PagingAndSortingRepository<UserFavorite, Integer>{
	
	
	public List<UserFavorite> findUserFavByUserId(int userid);

}

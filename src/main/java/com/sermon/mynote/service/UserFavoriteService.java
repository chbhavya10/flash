package com.sermon.mynote.service;



import java.util.List;

import com.sermon.mynote.domain.UserFavorite;

public interface UserFavoriteService {
	
	public UserFavorite save(UserFavorite userFavorite);
	
	public UserFavorite findById(Integer id);
	
	void delete(UserFavorite userFavorite);
   
	public List<UserFavorite> findUserFavByUserId(int userid);
	
	
}

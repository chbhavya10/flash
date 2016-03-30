package com.sermon.mynote.service;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sermon.mynote.domain.User;
import com.sermon.mynote.domain.UserProfile;



public interface UserProfileService {
	
	

	public UserProfile findById(Integer id);
	
	public UserProfile save(UserProfile userProfile);
	
	public int updateUserProfile(Integer userId,String firstName,String lastName,
			String nickName,String address1,String address2,
			int cityId,int stateId,int countryId,String  zipCode);


}

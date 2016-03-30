package com.sermon.mynote.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sermon.mynote.domain.VwUserprofile;

public interface VwUserprofileRepository extends PagingAndSortingRepository<VwUserprofile,Long>{

	public List<VwUserprofile> findUserProfileByUserId(int userid);
	
}

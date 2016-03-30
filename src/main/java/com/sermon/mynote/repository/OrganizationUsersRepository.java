package com.sermon.mynote.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sermon.mynote.domain.OrganizationUsers;
import com.sermon.mynote.domain.UserFavorite;



public interface OrganizationUsersRepository extends PagingAndSortingRepository<OrganizationUsers, Integer>{

	public List<OrganizationUsers> findOrgUserById(int userid);
}

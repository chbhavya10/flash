package com.sermon.mynote.service.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.sermon.mynote.domain.SearchOrganization;
import com.sermon.mynote.domain.VwUserorganizations;
import com.sermon.mynote.domain.VwUserprofile;
import com.sermon.mynote.repository.VwUserorganizationsRepository;
import com.sermon.mynote.repository.VwUserprofileRepository;
import com.sermon.mynote.service.VwUserprofileService;

@Service("vwUserProfileService")
@Repository
@Transactional
public class VwUserprofileServiceImpl implements VwUserprofileService{
	
	
	@Autowired
	private VwUserprofileRepository vwUserprofileRepository;
	
	@PersistenceContext
    private EntityManager em;
	
	/*
	@Transactional(readOnly=true)
	public List<VwUserprofile> findUserProfileByUserId1(int userid){
		
		return Lists.newArrayList(vwUserprofileRepository.findUserProfileByUserId(userid));
		
	}*/
	
	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<VwUserprofile> findUserProfileByUserId(int userid){

		TypedQuery<VwUserprofile> query = (TypedQuery<VwUserprofile>) em
				.createNativeQuery(
						"select o.* from  vw_userprofile o "
						+ " WHERE (o.UserId= :UserId) ",
						VwUserprofile.class).setParameter("UserId", userid );
		  
	          System.out.println(query.toString());
	       	  
			  List<VwUserprofile> results = (List<VwUserprofile>)query.getResultList();
			  return results;
			  	
	}
	


}

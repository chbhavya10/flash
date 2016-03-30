package com.sermon.mynote.service.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.sermon.mynote.domain.OrganizationUsers;
import com.sermon.mynote.domain.UserFavorite;
import com.sermon.mynote.repository.OrganizationUsersRepository;
import com.sermon.mynote.repository.UserFavoriteRepository;
import com.sermon.mynote.service.OrganizationUsersService;


@Service("organizationUsersService")
@Repository
@Transactional
public class OrganizationUsersServiceImpl implements OrganizationUsersService{
	
	
	 final Logger logger = LoggerFactory.getLogger(OrganizationUsers.class);	
		
		@PersistenceContext
	    private EntityManager em;
		
		@Autowired
		private OrganizationUsersRepository organizationUsersRepository;
		
		public OrganizationUsers save(OrganizationUsers organizationUsers)
		{
			return organizationUsersRepository.save(organizationUsers);
		}
		
		
	

		@Transactional(readOnly=true)
		public OrganizationUsers findById(Integer id) {
			return organizationUsersRepository.findOne(id);
		}
			
		@Transactional(readOnly=true)
		public List<OrganizationUsers> findOrgUserById(int userid)
		{
			return Lists.newArrayList(organizationUsersRepository.findOrgUserById(userid));	
		}
		
		
		
		
		@Override
	    public void delete(OrganizationUsers organizationUsers) {
			OrganizationUsers mergedOrgUser = em.merge(organizationUsers);
	        em.remove(mergedOrgUser);
	        logger.info("User Favorite with id: " + organizationUsers.getOrgUserId() + " deleted successfully");
	    }


	
}

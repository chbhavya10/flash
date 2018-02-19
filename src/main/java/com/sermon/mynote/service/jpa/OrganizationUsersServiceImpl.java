package com.sermon.mynote.service.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.sermon.mynote.domain.OrganizationUsers;
import com.sermon.mynote.domain.StatusResponse;
import com.sermon.mynote.repository.OrganizationUsersRepository;
import com.sermon.mynote.service.OrganizationUsersService;

@Service("organizationUsersService")
@Repository
@Transactional
public class OrganizationUsersServiceImpl implements OrganizationUsersService {

	final Logger logger = LoggerFactory.getLogger(OrganizationUsers.class);

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private OrganizationUsersRepository organizationUsersRepository;
	public StatusResponse save(OrganizationUsers organizationUsers) {
		StatusResponse statusResponse = new StatusResponse();
		List<OrganizationUsers> mathcedList = new ArrayList<OrganizationUsers>();
		try{
		
		 mathcedList = Lists.newArrayList(organizationUsersRepository.findOrgUserById(organizationUsers.getUserId(), organizationUsers.getOrganizationId()));
		
		}catch(InvalidDataAccessApiUsageException e){
			
		}
		OrganizationUsers favoratedOrg = null;
		if(mathcedList.size() == 0){
			 favoratedOrg = organizationUsersRepository.save(organizationUsers);
			 if(favoratedOrg != null){
				 statusResponse.setStatus(true);
				 statusResponse.setMessage("Successfully updated the organization as favourite");
			 }else{
				 statusResponse.setStatus(false);
				 statusResponse.setMessage("Something went wrong");
			 }
		}
		
		return statusResponse;
	}
	

	@Transactional(readOnly = true)
	public OrganizationUsers findById(Integer id) {
		return organizationUsersRepository.findOne(id);
	}

	@Transactional(readOnly = true)
	public List<OrganizationUsers> findOrgUserById(int userid) {
		return Lists.newArrayList(organizationUsersRepository.findOrgUserById(userid));
	}

	@Override
	public void delete(OrganizationUsers organizationUsers) {
		OrganizationUsers mergedOrgUser = em.merge(organizationUsers);
		em.remove(mergedOrgUser);
		
		
		logger.info("User Favorite with id: " + organizationUsers.getOrgUserId() + " deleted successfully");
	}

}

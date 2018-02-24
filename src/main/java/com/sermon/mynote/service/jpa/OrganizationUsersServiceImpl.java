package com.sermon.mynote.service.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
	
		List<OrganizationUsers> mathcedList  = organizationUsersRepository.findOrgUserById(organizationUsers.getUserId());
		
		boolean dataExists = checkChurchisFavourite(mathcedList, organizationUsers.getOrganizationId());
		
		if (!dataExists) {
			OrganizationUsers favoratedOrg = null;
			
				favoratedOrg = organizationUsersRepository.save(organizationUsers);
				System.out.println(favoratedOrg.getOrgUserId());
				if (favoratedOrg != null) {
					statusResponse.setStatus(true);
					statusResponse.setMessage("Successfully updated the organization as favourite");
				} else {
					statusResponse.setStatus(false);
					statusResponse.setMessage("Something went wrong");
				}
			
		}else{
			statusResponse.setStatus(false);
			statusResponse.setMessage("Organization is  Already a Favourite");
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
		/*StatusResponse statusResponse = new StatusResponse();

		Query query = em.createQuery("DELETE FROM organizationusers WHERE OrgUserId=  " + organizationUsers.getOrganizationId());
		int deleted = query.executeUpdate();
		
		System.out.println(deleted);
		
		if (deleted > 0) {
			statusResponse.setStatus(true);
			statusResponse.setMessage("Event Deleted Sucessfully");
		} else {
			statusResponse.setStatus(false);
			statusResponse.setMessage("Event Deletion is Failure");
		}*/
		logger.info("User Favorite with id: " + organizationUsers.getOrgUserId() + " deleted successfully");
	}


	@Override
	public void deleteByUserIdAndOrganizationId(int userId, int organizationId) {
		List<OrganizationUsers> matchedList  = organizationUsersRepository.findOrgUserById(userId);
		int orgUserId = returnFavouriteChurhOrgUserId(matchedList , organizationId);
		if(orgUserId > 0){
			OrganizationUsers orgUser = findById(orgUserId);
			delete(orgUser);
		}
	}
	

	private boolean checkChurchisFavourite(List<OrganizationUsers> matchedList, int organizationId) {
		boolean recordExisted = false;
		
		for(OrganizationUsers organizationuser : matchedList){
			if(organizationId == organizationuser.getOrganizationId()){
				recordExisted = true;
				break;
			}
		}
		return recordExisted;
	}
	private int returnFavouriteChurhOrgUserId(List<OrganizationUsers> matchedList, int organizationId) {
		int  orgUserId = 0;
		
		for(OrganizationUsers organizationuser : matchedList){
			if(organizationId == organizationuser.getOrganizationId()){
				orgUserId = organizationuser.getOrgUserId();
				return orgUserId;
			}
		}
		return 0;
	}
	

}

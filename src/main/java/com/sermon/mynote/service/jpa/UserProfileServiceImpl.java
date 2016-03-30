package com.sermon.mynote.service.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sermon.mynote.domain.UserProfile;
import com.sermon.mynote.repository.UserProfileRepository;
import com.sermon.mynote.service.UserProfileService;

@Service("userProfileService")
@Repository
@Transactional
public class UserProfileServiceImpl implements UserProfileService {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private UserProfileRepository userProfileRepository;

	@Transactional(readOnly = true)
	public UserProfile findById(Integer id) {

		return userProfileRepository.findOne(id.intValue());
	}

	public UserProfile save(UserProfile userProfile) {

		return userProfileRepository.save(userProfile);
	}

	public int updateUserProfile(Integer userId, String firstName, String lastName, String nickName, String address1,
			String address2, int cityId, int stateId, int countryId, String zipCode) {

		StoredProcedureQuery proc = em.createNamedStoredProcedureQuery("UserProfile.update_userProfile");

		proc.setParameter("userId", userId).setParameter("firstName", firstName).setParameter("lastName", lastName)
				.setParameter("nickName", nickName).setParameter("address1", address1)
				.setParameter("address2", address2).setParameter("cityId", cityId).setParameter("stateId", stateId)
				.setParameter("countryId", countryId).setParameter("zipCode", zipCode)
				// .setParameter("dob", dob)
				// .setParameter("gender", gender)

		;

		int result = proc.executeUpdate();

		return result;

	}

}

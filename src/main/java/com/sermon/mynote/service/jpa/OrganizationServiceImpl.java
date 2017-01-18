package com.sermon.mynote.service.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.sermon.mynote.domain.Organization;
import com.sermon.mynote.repository.OrganizationRepository;
import com.sermon.mynote.service.OrganizationService;

@Service("organizationService")
@Repository
@Transactional
public class OrganizationServiceImpl implements OrganizationService {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private OrganizationRepository organizationRepository;

	@Transactional(readOnly = true)
	public List<Organization> findChurchesByOrganization() {

		return Lists.newArrayList(organizationRepository.findChurchesByUser());

	}

	@Override
	public int updateOrganization(int organizationId, String address1, String address2, int cityId, int stateId,
			int countryID, String zipCode) {

		StoredProcedureQuery proc = em.createNamedStoredProcedureQuery("Organization.update_organization");
		proc.setParameter("organizationId", organizationId).setParameter("address1", address1)
				.setParameter("address2", address2).setParameter("cityId", cityId).setParameter("stateId", stateId)
				.setParameter("countryId", countryID).setParameter("zipCode", zipCode);

		int result = proc.executeUpdate();
		return result;

	}

	/*
	 * @Transactional(readOnly=true) public List<Organization>
	 * SearchOrganizationTemp(String organizationName) {
	 * 
	 * TypedQuery<Organization> query = em.createQuery(
	 * "SELECT o.organizationId,o.organizationName,o.address1,o.address2,o.zipCode,c.cityName FROM Organization o WHERE (:organizationName ='All%' or o.organizationName like :organizationName)"
	 * , Organization.class).setParameter("organizationName",
	 * organizationName+"%"); List<Organization> results =
	 * (List<Organization>)query.getResultList(); return results;
	 * 
	 * }
	 */

}

package com.sermon.mynote.service.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sermon.mynote.domain.VwOrganizationInfo;
import com.sermon.mynote.service.VwOrganizationInfoService;

@Service("vwOrganizationInfoService")
@Repository
@Transactional
public class VwOrganizationInfoServiceImpl implements VwOrganizationInfoService {

	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional(readOnly = true)
	public List<VwOrganizationInfo> findOrganizationInfoByOrgId(int orgId) {

		@SuppressWarnings("unchecked")
		TypedQuery<VwOrganizationInfo> query = (TypedQuery<VwOrganizationInfo>) em.createNativeQuery(
				"select o.* from  vw_organizationInfo o " + " WHERE (o.OrganizationId= :OrganizationId) ",
				VwOrganizationInfo.class).setParameter("OrganizationId", orgId);

		System.out.println(query.toString());

		List<VwOrganizationInfo> results = (List<VwOrganizationInfo>) query.getResultList();
		return results;

	}

	@Override
	public int updateOrgInfo(int organizationId, String website, String primaryEmail, String generalInfo, String hours,
			String facebookLink) {

		StoredProcedureQuery proc = em.createNamedStoredProcedureQuery("OrganizationInfo.update_orgInfo");

		proc.setParameter("organizationId", organizationId).setParameter("website", website)
				.setParameter("primaryEmail", primaryEmail).setParameter("generalInfo", generalInfo)
				.setParameter("hours", hours).setParameter("facebookLink", facebookLink);

		int result = proc.executeUpdate();

		return result;
	}
}

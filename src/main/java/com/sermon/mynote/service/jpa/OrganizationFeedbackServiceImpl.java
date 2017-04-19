package com.sermon.mynote.service.jpa;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sermon.mynote.domain.OrganizationFeedback;
import com.sermon.mynote.repository.OrganizationFeedbackRepository;
import com.sermon.mynote.service.OrganizationFeedbackService;

@Service("orgFeedbackService")
@Repository
@Transactional
public class OrganizationFeedbackServiceImpl implements OrganizationFeedbackService {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private OrganizationFeedbackRepository orgFeedbackRepository;

	@Override
	public int updateOrgFeedback(int organizationId, int userId, double rating, String feedbackComments) {

		StoredProcedureQuery proc = em.createNamedStoredProcedureQuery("OrganizationFeedback.update_org_feedback");
		proc.setParameter("organizationId", organizationId).setParameter("userId", userId)
				.setParameter("rating", rating).setParameter("feedbackComments", feedbackComments);

		int result = proc.executeUpdate();
		return result;
	}

	@Override
	public OrganizationFeedback getFeedback(int organizationId, int userId) {

		Integer orgFeedbackId = null;
		try {
			Query query = em
					.createNativeQuery(
							"select organizationFeedbackId returnvalue from OrganizationFeedback where organizationId=:organizationId and userId=:userId")
					.setParameter("organizationId", organizationId).setParameter("userId", userId);

			if (query.getSingleResult() != null) {
				orgFeedbackId = (Integer) query.getSingleResult();
			}
		} catch (NoResultException e) {

		}

		return orgFeedbackRepository.findOne(orgFeedbackId);
	}

}

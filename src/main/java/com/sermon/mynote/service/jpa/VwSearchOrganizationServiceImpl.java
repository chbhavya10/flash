package com.sermon.mynote.service.jpa;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sermon.mynote.domain.OrganizationLike;
import com.sermon.mynote.domain.SearchOrganization;
import com.sermon.mynote.service.VwSearchOrganizationService;

@Service("vwSearchOrganizationService")
@Repository
@Transactional
public class VwSearchOrganizationServiceImpl implements VwSearchOrganizationService {

	/*
	 * @Autowired private VwSearchOrganizationRepository
	 * vwSearchOrganizationRepository;
	 */

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<OrganizationLike> SearchOrganiz(String organizationName, String zipCode, String city) {

		TypedQuery<SearchOrganization> query = (TypedQuery<SearchOrganization>) em
				.createNativeQuery(
						"select o.OrganizationId,o.organizationName, o.Address1 AS Address1, o.Address2 AS Address2, c.CityName AS CityName,s.StateName AS StateName,cy.CountryName AS CountryName,o.ZipCode AS ZipCode from organization o join city c on ((o.CityID = c.CityId)) join state s on ((o.StateId = s.StateId)) join country cy on ((o.CountryID = cy.CountryID)) "
								+ "WHERE (:organizationName ='All%' or o.organizationName like :organizationName) AND (:zipCode='All%' or o.ZipCode like :zipCode) AND (:city='All%' or c.CityName like :city)",
						SearchOrganization.class)
				.setParameter("organizationName", organizationName + "%").setParameter("zipCode", zipCode + "%")
				.setParameter("city", city + "%");

		System.out.println(query.toString());

		List<SearchOrganization> results = (List<SearchOrganization>) query.getResultList();

		List<OrganizationLike> likes = new ArrayList<OrganizationLike>();

		for (SearchOrganization organization : results) {

			OrganizationLike like = new OrganizationLike();

			like.setAddress1(organization.getAddress1());
			like.setAddress2(organization.getAddress2());
			like.setCityName(organization.getCityName());
			like.setCountryName(organization.getCountryName());
			like.setOrganizationId(organization.getOrganizationId());
			like.setOrganizationName(organization.getOrganizationName());
			like.setStateName(organization.getStateName());
			like.setZipcode(organization.getZipcode());
			Integer likeCount = 0;
			try {
				Query likeQuery = em
						.createNativeQuery(
								"SELECT COUNT(*) AS likedSermons FROM `note` n, `NoteLike` nl WHERE n.`NoteId`=nl.`NoteId` AND n.`OrganizationId`=:organizationId")
						.setParameter("organizationId", like.getOrganizationId());
				System.out.println(likeQuery);

				if (likeQuery.getSingleResult() != null) {
					likeCount = ((BigInteger) likeQuery.getSingleResult()).intValue();
				}
			} catch (NoResultException e) {

			}
			like.setLikeCount(likeCount);
			Integer downloadCount = 0;
			try {
				Query downloadQuery = em
						.createNativeQuery(
								"SELECT COUNT(*) AS downloadedSermons FROM `note` n,`NoteDownload` nd WHERE n.`NoteId`=nd.`NoteId` AND n.`OrganizationId`=:organizationId")
						.setParameter("organizationId", like.getOrganizationId());
				System.out.println(downloadQuery);

				if (downloadQuery.getSingleResult() != null) {
					downloadCount = ((BigInteger) downloadQuery.getSingleResult()).intValue();
				}
			} catch (NoResultException e) {

			}
			like.setDownloadCount(downloadCount);

			Integer sermonCount = 0;
			try {
				Query sermonQuery = em
						.createNativeQuery(
								"SELECT COUNT(*) AS sermonCount FROM `note` WHERE `OrganizationId`=:organizationId")
						.setParameter("organizationId", like.getOrganizationId());
				System.out.println(sermonQuery);

				if (sermonQuery.getSingleResult() != null) {
					sermonCount = ((BigInteger) sermonQuery.getSingleResult()).intValue();
				}
			} catch (NoResultException e) {

			}
			like.setSermonCount(sermonCount);

			likes.add(like);
		}

		return likes;

	}

}

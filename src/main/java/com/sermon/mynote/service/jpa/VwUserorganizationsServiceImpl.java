package com.sermon.mynote.service.jpa;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.sermon.mynote.domain.UserOrgList;
import com.sermon.mynote.domain.VwUserorganizations;
import com.sermon.mynote.repository.VwUserorganizationsRepository;
import com.sermon.mynote.service.VwUserorganizationsService;

@Service("vworganizationService")
@Repository
@Transactional
public class VwUserorganizationsServiceImpl implements VwUserorganizationsService {

	@Autowired
	private VwUserorganizationsRepository vwUserorganizationsRepository;

	@PersistenceContext
	private EntityManager em;

	@Transactional(readOnly = true)
	public List<UserOrgList> findOrganizationsByUser(int userid) {

		List<VwUserorganizations> orgList = Lists
				.newArrayList(vwUserorganizationsRepository.findOrganizationsByUser(userid));

		List<UserOrgList> userOrgLists = new ArrayList<>();

		for (VwUserorganizations org : orgList) {

			UserOrgList list = new UserOrgList();

			list.setCityName(org.getCityName());
			list.setCountryName(org.getCountryName());
			list.setOrganizationId(org.getorganizationId());
			list.setOrganizationName(org.getOrganizationName());
			list.setOrgUserId(org.getOrguserid());
			list.setStateName(org.getStateName());
			list.setUserId(org.getUserid());
			list.setUserName(org.getUsername());
			list.setZipCode(org.getZipcode());

			Integer likeCount = 0;
			try {
				Query likeQuery = em
						.createNativeQuery(
								"SELECT COUNT(*) AS likedSermons FROM `note` n, `NoteLike` nl WHERE n.`NoteId`=nl.`NoteId` AND n.`OrganizationId`=:organizationId AND `LikeCount`=1")
						.setParameter("organizationId", list.getOrganizationId());
				System.out.println(likeQuery);

				if (likeQuery.getSingleResult() != null) {
					likeCount = ((BigInteger) likeQuery.getSingleResult()).intValue();
				}
			} catch (NoResultException e) {

			}
			list.setLikeCount(likeCount);
			Integer downloadCount = 0;
			try {
				Query downloadQuery = em
						.createNativeQuery(
								"SELECT COUNT(*) AS downloadedSermons FROM `note` n,`NoteDownload` nd WHERE n.`NoteId`=nd.`NoteId` AND n.`OrganizationId`=:organizationId")
						.setParameter("organizationId", list.getOrganizationId());
				System.out.println(downloadQuery);

				if (downloadQuery.getSingleResult() != null) {
					downloadCount = ((BigInteger) downloadQuery.getSingleResult()).intValue();
				}
			} catch (NoResultException e) {

			}
			list.setDownloadCount(downloadCount);

			Integer sermonCount = 0;
			try {
				Query sermonQuery = em
						.createNativeQuery(
								"SELECT COUNT(*) AS sermonCount FROM `note` WHERE `OrganizationId`=:organizationId AND `Published`='Y'")
						.setParameter("organizationId", list.getOrganizationId());
				System.out.println(sermonQuery);

				if (sermonQuery.getSingleResult() != null) {
					sermonCount = ((BigInteger) sermonQuery.getSingleResult()).intValue();
				}
			} catch (NoResultException e) {

			}
			list.setSermonCount(sermonCount);

			userOrgLists.add(list);
		}
		return userOrgLists;
		
	}

}

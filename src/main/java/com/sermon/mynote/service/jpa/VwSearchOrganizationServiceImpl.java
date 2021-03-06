package com.sermon.mynote.service.jpa;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.sermon.mynote.domain.City;
import com.sermon.mynote.domain.Country;
import com.sermon.mynote.domain.Denomination;
import com.sermon.mynote.domain.LimitParameters;
import com.sermon.mynote.domain.OrganizationLike;
import com.sermon.mynote.domain.SearchOrg;
import com.sermon.mynote.domain.SearchOrganization;
import com.sermon.mynote.domain.SearchOrganizationResult;
import com.sermon.mynote.domain.State;
import com.sermon.mynote.domain.VwUserorganizations;
import com.sermon.mynote.repository.VwUserorganizationsRepository;
import com.sermon.mynote.service.NoteService;
import com.sermon.mynote.service.VwSearchOrganizationService;
import com.sermon.util.AppConstants;

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

	@Value("${s3.aws.bucket.name}")
	private String s3BucketName;

	@Value("${org.image.bucket.path}")
	private String orgImageBucketPath;

	@Autowired
	private NoteService noteService;

	@Autowired
	private VwUserorganizationsRepository vwUserorganizationsRepository;

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<OrganizationLike> SearchOrganiz(String organizationName, String zipCode, String city,
			String denomination) {

		TypedQuery<SearchOrg> query = (TypedQuery<SearchOrg>) em
				.createNativeQuery(
						"select o.OrganizationId,o.organizationName, o.Address1 AS Address1, o.Address2 AS Address2, c.CityName AS CityName, c.CityId AS CityId, s.StateName AS StateName, s.StateId AS StateId, cy.CountryName AS CountryName, cy.CountryID AS CountryID, o.ZipCode AS ZipCode,d.Denomination AS Denomination,d.DenominationID AS DenominationID,o.OrgImage AS OrgImage,oi.Phone AS Phone from organization o join city c on ((o.CityID = c.CityId)) join state s on ((o.StateId = s.StateId)) join country cy on ((o.CountryID = cy.CountryID)) join Denomination d on ((o.DenominationId = d.DenominationID)) join OrganizationInfo oi on ((o.OrganizationId = oi.OrganizationId)) "
								+ "WHERE `ValidationKey` IS NOT NULL AND (:organizationName ='All%' or o.organizationName like :organizationName) AND (:zipCode='All%' or o.ZipCode like :zipCode) AND (:city='All%' or c.CityName like :city) AND (:denomination='All%' or d.Denomination like :denomination)",
						SearchOrg.class)
				.setParameter("organizationName", organizationName + "%").setParameter("zipCode", zipCode + "%")
				.setParameter("city", city + "%").setParameter("denomination", denomination + "%");

		List<SearchOrg> results = (List<SearchOrg>) query.getResultList();

		List<OrganizationLike> likes = new ArrayList<OrganizationLike>();
		String bucketName = s3BucketName + AppConstants.SLASH + orgImageBucketPath;
		Integer eventCount = 0;
		for (SearchOrg organization : results) {
			OrganizationLike like = new OrganizationLike();

			try {
				Query getEventCount = em.createNativeQuery("SELECT COUNT(*) FROM `organization` WHERE `OrganizationId`="
						+ organization.getOrganizationId());

				eventCount = ((BigInteger) getEventCount.getSingleResult()).intValue();
				// return eventCount;

			} catch (NoResultException e) {
				e.printStackTrace();
			}
			// return 0;

			like.setEventCount(eventCount);
			like.setAddress1(organization.getAddress1());
			like.setAddress2(organization.getAddress2());
			like.setCityName(organization.getCityName());
			like.setCountryName(organization.getCountryName());
			like.setOrganizationId(organization.getOrganizationId());
			like.setOrganizationName(organization.getOrganizationName());
			like.setStateName(organization.getStateName());
			like.setZipcode(organization.getZipcode());
			like.setDenomination(organization.getDenomination());
			like.setCityId(organization.getCityId());
			like.setStateId(organization.getStateId());
			like.setCountryId(organization.getCountryId());
			like.setDenominationId(organization.getDenominationId());
			like.setPhone(organization.getPhone());

			String orgImg = organization.getOrgImage();
			String orgImgPath = null;

			if (orgImg != null) {

				String s3Obj = organization.getOrganizationId() + AppConstants.SLASH + orgImg;
				orgImgPath = noteService.generatePreSignedURL(bucketName, s3Obj);
				like.setOrgImage(orgImgPath);
			} else {
				String s3Obj = AppConstants.DEFAULT_ID + AppConstants.SLASH + AppConstants.DEFAULT_ORG_IMAGE;
				orgImgPath = noteService.generatePreSignedURL(bucketName, s3Obj);
				like.setOrgImage(orgImgPath);
			}

			Integer likeCount = 0;
			try {
				Query likeQuery = em
						.createNativeQuery(
								"SELECT COUNT(*) AS likedSermons FROM `note` n, `NoteLike` nl WHERE n.`NoteId`=nl.`NoteId` AND n.`OrganizationId`=:organizationId AND `LikeCount`=1")
						.setParameter("organizationId", like.getOrganizationId());

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
								"SELECT COUNT(*) AS sermonCount FROM `note` WHERE `OrganizationId`=:organizationId AND `Published`='Y'")
						.setParameter("organizationId", like.getOrganizationId());

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

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public SearchOrganizationResult SearchOrganiz(String orgname, String zipcode, String city, String denomination,
			LimitParameters limitParameters) {

		Integer eventCount = 0;

		if (limitParameters.getLength() == 0) {
			limitParameters.setLength(20);
		}

		TypedQuery<SearchOrg> query = (TypedQuery<SearchOrg>) em
				.createNativeQuery(
						"select o.OrganizationId,o.organizationName, o.Address1 AS Address1, o.Address2 AS Address2, c.CityName AS CityName,c.CityId AS CityId,s.StateName AS StateName,s.StateId AS StateId,cy.CountryName AS CountryName,cy.CountryID AS CountryID,o.ZipCode AS ZipCode,d.Denomination AS Denomination,d.DenominationID AS DenominationID,o.OrgImage AS OrgImage,oi.Phone AS Phone from organization o left outer join city c on ((o.CityID = c.CityId)) left outer join state s on ((o.StateId = s.StateId)) left outer join country cy on ((o.CountryID = cy.CountryID)) left outer join Denomination d on ((o.DenominationId = d.DenominationID)) left outer join OrganizationInfo oi on ((o.OrganizationId = oi.OrganizationId)) "
								+ "WHERE (:organizationName ='%All%' or o.organizationName like :organizationName) AND (:zipCode='%All%' or o.ZipCode like :zipCode) AND (:city='%All%' or c.CityName like :city) AND (:denomination='%All%' or d.Denomination like :denomination) order by o.OrganizationId limit :start,:length",
						SearchOrg.class)
				.setParameter("organizationName", "%" + orgname + "%").setParameter("zipCode", "%" + zipcode + "%")
				.setParameter("city", "%" + city + "%").setParameter("denomination", "%" + denomination + "%")
				.setParameter("start", limitParameters.getStart()).setParameter("length", limitParameters.getLength());

		List<SearchOrg> results = (List<SearchOrg>) query.getResultList();

		BigInteger totalOrgCount = null;

		try {
			Query totalCount = em
					.createNativeQuery(
							"select count(*) AS count from organization o left outer join city c on ((o.CityID = c.CityId)) left outer join state s on ((o.StateId = s.StateId)) left outer join country cy on ((o.CountryID = cy.CountryID)) left outer join Denomination d on ((o.DenominationId = d.DenominationID)) "
									+ "WHERE (:organizationName ='%All%' or o.organizationName like :organizationName) AND (:zipCode='%All%' or o.ZipCode like :zipCode) AND (:city='%All%' or c.CityName like :city) AND (:denomination='%All%' or d.Denomination like :denomination)")
					.setParameter("organizationName", "%" + orgname + "%").setParameter("zipCode", "%" + zipcode + "%")
					.setParameter("city", "%" + city + "%").setParameter("denomination", "%" + denomination + "%");

			if (totalCount.getSingleResult() != null) {
				totalOrgCount = (BigInteger) totalCount.getSingleResult();
			}
		} catch (NoResultException e) {

		}

		System.out.println("total count : " + totalOrgCount);

		List<OrganizationLike> likes = new ArrayList<OrganizationLike>();
		Set<Country> countries = new HashSet<>();
		Set<State> states = new HashSet<>();
		Set<City> cities = new HashSet<>();
		Set<Denomination> denominations = new HashSet<>();

		String bucketName = s3BucketName + AppConstants.SLASH + orgImageBucketPath;

		for (SearchOrg organization : results) {

			OrganizationLike like = new OrganizationLike();
			Denomination denominatio = new Denomination();
			City citi = new City();
			State state = new State();
			Country country = new Country();

			if (organization.getCountryId() != null) {
				country.setCountryId(organization.getCountryId());
				country.setCountryName(organization.getCountryName());
			}

			if (organization.getStateId() != null) {
				state.setStateId(organization.getStateId());
				state.setStateName(organization.getStateName());
				state.setCountryId(organization.getCountryId());
			}

			if (organization.getCityId() != null) {
				citi.setCityId(organization.getCityId());
				citi.setCityName(organization.getCityName());
				citi.setStateId(organization.getStateId());
			}

			if (organization.getDenominationId() != null) {
				denominatio.setDenominationId(organization.getDenominationId());
				denominatio.setDenomination(organization.getDenomination());
			}

			try {
	
				Query getEventCount = em.createNativeQuery("SELECT COUNT(*) FROM Event WHERE OrganizationID="+organization.getOrganizationId()+" AND ToDate > NOW() OR ToDate = NOW()");


				eventCount = ((BigInteger) getEventCount.getSingleResult()).intValue();
				// return eventCount;

			} catch (NoResultException e) {
				e.printStackTrace();
			}
			
			like.setEventCount(eventCount);
			like.setAddress1(organization.getAddress1());
			like.setAddress2(organization.getAddress2());
			like.setCityName(organization.getCityName());
			like.setCountryName(organization.getCountryName());
			like.setOrganizationId(organization.getOrganizationId());
			like.setOrganizationName(organization.getOrganizationName());
			like.setStateName(organization.getStateName());
			like.setZipcode(organization.getZipcode());
			like.setDenomination(organization.getDenomination());
			like.setCityId(organization.getCityId());
			like.setStateId(organization.getStateId());
			like.setCountryId(organization.getCountryId());
			like.setDenominationId(organization.getDenominationId());
			like.setPhone(organization.getPhone());

			String orgImg = organization.getOrgImage();
			String orgImgPath = null;

			if (orgImg != null) {

				String s3Obj = organization.getOrganizationId() + AppConstants.SLASH + orgImg;
				orgImgPath = noteService.generatePreSignedURL(bucketName, s3Obj);
				like.setOrgImage(orgImgPath);
			} else {
				String s3Obj = AppConstants.DEFAULT_ID + AppConstants.SLASH + AppConstants.DEFAULT_ORG_IMAGE;
				orgImgPath = noteService.generatePreSignedURL(bucketName, s3Obj);
				like.setOrgImage(orgImgPath);
			}

			Integer likeCount = 0;
			try {
				Query likeQuery = em
						.createNativeQuery(
								"SELECT COUNT(*) AS likedSermons FROM `note` n, `NoteLike` nl WHERE n.`NoteId`=nl.`NoteId` AND n.`OrganizationId`=:organizationId AND `LikeCount`=1")
						.setParameter("organizationId", like.getOrganizationId());

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
								"SELECT COUNT(*) AS sermonCount FROM `note` WHERE `OrganizationId`=:organizationId AND `Published`='Y'")
						.setParameter("organizationId", like.getOrganizationId());

				if (sermonQuery.getSingleResult() != null) {
					sermonCount = ((BigInteger) sermonQuery.getSingleResult()).intValue();
				}
			} catch (NoResultException e) {

			}
			like.setSermonCount(sermonCount);

			likes.add(like);
			denominations.add(denominatio);
			cities.add(citi);
			states.add(state);
			countries.add(country);
		}

		countries.remove(null);
		states.remove(null);
		cities.remove(null);
		denominations.remove(null);

		SearchOrganizationResult result = new SearchOrganizationResult();
		result.setTotalCount(totalOrgCount);
		result.setResult(likes);
		result.setCountries(countries);
		result.setStates(states);
		result.setCities(cities);
		result.setDenominations(denominations);

		return result;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<OrganizationLike> SearchOrganization(String orgname, String zipcode, String city, Integer userId) {
		TypedQuery<SearchOrganization> query = (TypedQuery<SearchOrganization>) em
				.createNativeQuery(
						"select o.OrganizationId,o.organizationName, o.Address1 AS Address1, o.Address2 AS Address2, c.CityName AS CityName,c.CityId AS CityId, s.StateName AS StateName, s.StateId AS StateId, cy.CountryName AS CountryName,cy.CountryID AS CountryID, o.ZipCode AS ZipCode,o.OrgImage AS OrgImage from organization o join city c on ((o.CityID = c.CityId)) join state s on ((o.StateId = s.StateId)) join country cy on ((o.CountryID = cy.CountryID)) "
								+ "WHERE `ValidationKey` IS NOT NULL AND (:organizationName ='All%' or o.organizationName like :organizationName) AND (:zipCode='All%' or o.ZipCode like :zipCode) AND (:city='All%' or c.CityName like :city)",
						SearchOrganization.class)
				.setParameter("organizationName", orgname + "%").setParameter("zipCode", zipcode + "%")
				.setParameter("city", city + "%");

		List<SearchOrganization> results = (List<SearchOrganization>) query.getResultList();

		List<OrganizationLike> likes = new ArrayList<OrganizationLike>();
		String bucketName = s3BucketName + AppConstants.SLASH + orgImageBucketPath;

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
			like.setCityId(organization.getCityId());
			like.setStateId(organization.getStateId());
			like.setCountryId(organization.getCountryId());

			String orgImg = organization.getOrgImage();
			String orgImgPath = null;

			if (orgImg != null) {

				String s3Obj = organization.getOrganizationId() + AppConstants.SLASH + orgImg;
				orgImgPath = noteService.generatePreSignedURL(bucketName, s3Obj);
				like.setOrgImage(orgImgPath);
			} else {
				String s3Obj = AppConstants.DEFAULT_ID + AppConstants.SLASH + AppConstants.DEFAULT_ORG_IMAGE;
				orgImgPath = noteService.generatePreSignedURL(bucketName, s3Obj);
				like.setOrgImage(orgImgPath);
			}

			Integer likeCount = 0;
			try {
				Query likeQuery = em
						.createNativeQuery(
								"SELECT COUNT(*) AS likedSermons FROM `note` n, `NoteLike` nl WHERE n.`NoteId`=nl.`NoteId` AND n.`OrganizationId`=:organizationId AND `LikeCount`=1")
						.setParameter("organizationId", like.getOrganizationId());

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
								"SELECT COUNT(*) AS sermonCount FROM `note` WHERE `OrganizationId`=:organizationId AND `Published`='Y'")
						.setParameter("organizationId", like.getOrganizationId());

				if (sermonQuery.getSingleResult() != null) {
					sermonCount = ((BigInteger) sermonQuery.getSingleResult()).intValue();
				}
			} catch (NoResultException e) {

			}
			like.setSermonCount(sermonCount);

			List<VwUserorganizations> orgList = Lists.newArrayList(
					vwUserorganizationsRepository.findOrganizationsByUser(userId, organization.getOrganizationId()));

			boolean isFavorate = (orgList.size() != 0) ? true : false;
			like.setFavorate(isFavorate);

			likes.add(like);
		}

		return likes;
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public SearchOrganizationResult SearchOrganiz(String orgname, String zipcode, String city,
			LimitParameters limitParameters) {

		if (limitParameters.getLength() == 0) {
			limitParameters.setLength(20);
		}

		TypedQuery<SearchOrganization> query = (TypedQuery<SearchOrganization>) em
				.createNativeQuery(
						"select o.OrganizationId,o.organizationName, o.Address1 AS Address1, o.Address2 AS Address2, c.CityName AS CityName, c.CityId AS CityId,s.StateName AS StateName,s.StateId AS StateId,cy.CountryName AS CountryName,cy.CountryID AS CountryID,o.ZipCode AS ZipCode,o.OrgImage AS OrgImage from organization o join city c on ((o.CityID = c.CityId)) join state s on ((o.StateId = s.StateId)) join country cy on ((o.CountryID = cy.CountryID)) "
								+ "WHERE (:organizationName ='All%' or o.organizationName like :organizationName) AND (:zipCode='All%' or o.ZipCode like :zipCode) AND (:city='All%' or c.CityName like :city) order by o.OrganizationId limit :start,:length",
						SearchOrganization.class)
				.setParameter("organizationName", orgname + "%").setParameter("zipCode", zipcode + "%")
				.setParameter("city", city + "%").setParameter("start", limitParameters.getStart())
				.setParameter("length", limitParameters.getLength());

		List<SearchOrganization> results = (List<SearchOrganization>) query.getResultList();

		BigInteger totalOrgCount = null;

		try {
			Query totalCount = em
					.createNativeQuery(
							"select count(*) AS count from organization o join city c on ((o.CityID = c.CityId)) join state s on ((o.StateId = s.StateId)) join country cy on ((o.CountryID = cy.CountryID)) "
									+ "WHERE (:organizationName ='All%' or o.organizationName like :organizationName) AND (:zipCode='All%' or o.ZipCode like :zipCode) AND (:city='All%' or c.CityName like :city)")
					.setParameter("organizationName", orgname + "%").setParameter("zipCode", zipcode + "%")
					.setParameter("city", city + "%");

			if (totalCount.getSingleResult() != null) {
				totalOrgCount = (BigInteger) totalCount.getSingleResult();
			}
		} catch (NoResultException e) {

		}

		System.out.println("total count : " + totalOrgCount);

		List<OrganizationLike> likes = new ArrayList<OrganizationLike>();
		String bucketName = s3BucketName + AppConstants.SLASH + orgImageBucketPath;

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
			like.setCityId(organization.getCityId());
			like.setStateId(organization.getStateId());
			like.setCountryId(organization.getCountryId());

			String orgImg = organization.getOrgImage();
			String orgImgPath = null;

			if (orgImg != null) {

				String s3Obj = organization.getOrganizationId() + AppConstants.SLASH + orgImg;
				orgImgPath = noteService.generatePreSignedURL(bucketName, s3Obj);
				like.setOrgImage(orgImgPath);
			} else {
				String s3Obj = AppConstants.DEFAULT_ID + AppConstants.SLASH + AppConstants.DEFAULT_ORG_IMAGE;
				orgImgPath = noteService.generatePreSignedURL(bucketName, s3Obj);
				like.setOrgImage(orgImgPath);
			}

			Integer likeCount = 0;
			try {
				Query likeQuery = em
						.createNativeQuery(
								"SELECT COUNT(*) AS likedSermons FROM `note` n, `NoteLike` nl WHERE n.`NoteId`=nl.`NoteId` AND n.`OrganizationId`=:organizationId AND `LikeCount`=1")
						.setParameter("organizationId", like.getOrganizationId());

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
								"SELECT COUNT(*) AS sermonCount FROM `note` WHERE `OrganizationId`=:organizationId AND `Published`='Y'")
						.setParameter("organizationId", like.getOrganizationId());

				if (sermonQuery.getSingleResult() != null) {
					sermonCount = ((BigInteger) sermonQuery.getSingleResult()).intValue();
				}
			} catch (NoResultException e) {

			}
			like.setSermonCount(sermonCount);

			likes.add(like);
		}

		SearchOrganizationResult result = new SearchOrganizationResult();
		result.setTotalCount(totalOrgCount);
		result.setResult(likes);

		return result;
	}
}

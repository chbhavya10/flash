package com.sermon.mynote.service.jpa;

import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.google.common.collect.Lists;
import com.sermon.mynote.domain.UserOrgList;
import com.sermon.mynote.domain.VwUserorganizations;
import com.sermon.mynote.repository.VwUserorganizationsRepository;
import com.sermon.mynote.service.VwUserorganizationsService;
import com.sermon.util.AppConstants;

@Service("vworganizationService")
@Repository
@Transactional
public class VwUserorganizationsServiceImpl implements VwUserorganizationsService {

	@Autowired
	private VwUserorganizationsRepository vwUserorganizationsRepository;

	@PersistenceContext
	private EntityManager em;

	@Value("${s3.aws.access.key.id}")
	private String awsAccessKeyId;

	@Value("${s3.aws.access.secret.key}")
	private String awsAccessSecretKey;

	@Value("${s3.aws.bucket.name}")
	private String s3BucketName;

	@Value("${org.image.bucket.path}")
	private String orgImageBucketPath;

	@Value("${amazon.link}")
	private String amazonLink;

	@Transactional(readOnly = true)
	public List<UserOrgList> findOrganizationsByUser(int userid) {

		List<VwUserorganizations> orgList = Lists
				.newArrayList(vwUserorganizationsRepository.findOrganizationsByUser(userid));

		List<UserOrgList> userOrgLists = new ArrayList<>();
		String bucketName = s3BucketName + AppConstants.SLASH + orgImageBucketPath;

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

			String orgImgPath = null;
			String orgImg = org.getOrgImage();

			if (orgImg != null) {

				String s3Obj = org.getorganizationId() + AppConstants.SLASH + orgImg;
				orgImgPath = generatePreSignedURL(bucketName, s3Obj);
				list.setOrgImage(orgImgPath);
			} else {
				String s3Obj = AppConstants.DEFAULT_ID + AppConstants.SLASH + AppConstants.DEFAULT_ORG_IMAGE;
				orgImgPath = generatePreSignedURL(bucketName, s3Obj);
				list.setOrgImage(orgImgPath);
			}

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

	private String generatePreSignedURL(String bucketName, String objectKey) {

		java.util.Date expiration = new java.util.Date();
		long milliSeconds = expiration.getTime();
		milliSeconds += AppConstants.EXPIRY_SECONDS;
		expiration.setTime(milliSeconds);

		GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName,
				objectKey);
		generatePresignedUrlRequest.setMethod(HttpMethod.GET);
		generatePresignedUrlRequest.setExpiration(expiration);
		AmazonS3 ams3 = getAmazonS3Client();
		ams3.setEndpoint(AppConstants.AMAZON_LINK);

		URL url = ams3.generatePresignedUrl(generatePresignedUrlRequest);

		return url.toString();

	}

	private AmazonS3 getAmazonS3Client() {

		AWSCredentials credentials = new BasicAWSCredentials(awsAccessKeyId, awsAccessSecretKey);
		return new AmazonS3Client(credentials);
	}

}

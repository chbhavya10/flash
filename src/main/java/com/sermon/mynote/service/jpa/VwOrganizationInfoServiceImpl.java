package com.sermon.mynote.service.jpa;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.math.*;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.amazonaws.util.IOUtils;
import com.sermon.mynote.domain.VwOrgInfo;
import com.sermon.mynote.domain.VwOrganizationInfo;
import com.sermon.mynote.service.NoteService;
import com.sermon.mynote.service.VwOrganizationInfoService;
import com.sermon.util.AppConstants;

@Service("vwOrganizationInfoService")
@Repository
@Transactional
public class VwOrganizationInfoServiceImpl implements VwOrganizationInfoService {

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

	@Autowired
	private NoteService noteService;

	final Logger logger = LoggerFactory.getLogger(VwOrganizationInfoServiceImpl.class);

	@Override
	@Transactional(readOnly = true)
	public List<VwOrgInfo> findOrganizationInfoByOrgId(int orgId) {

		@SuppressWarnings("unchecked")
		TypedQuery<VwOrganizationInfo> query = (TypedQuery<VwOrganizationInfo>) em.createNativeQuery(
				"select o.* from  vw_organizationinfo o " + " WHERE (o.OrganizationId= :OrganizationId) ",
				VwOrganizationInfo.class).setParameter("OrganizationId", orgId);
		System.out.println(query.toString());

		List<VwOrganizationInfo> results = (List<VwOrganizationInfo>) query.getResultList();
		 List<VwOrgInfo> vwOrgInfos = new ArrayList<VwOrgInfo>();

		for (VwOrganizationInfo info : results) {

			String bucketName = s3BucketName + AppConstants.SLASH + orgImageBucketPath;
			String orgImgPath = null;
			String orgImg = info.getOrgImage();

			if (orgImg != null) {

				String s3Obj = info.getOrganizationId() + AppConstants.SLASH + orgImg;
				orgImgPath = noteService.generatePreSignedURL(bucketName, s3Obj);
				info.setOrgImage(orgImgPath);
			} else {
				String s3Obj = AppConstants.DEFAULT_ID + AppConstants.SLASH + AppConstants.DEFAULT_ORG_IMAGE;
				orgImgPath = noteService.generatePreSignedURL(bucketName, s3Obj);
				info.setOrgImage(orgImgPath);
			}
			
			   
		    VwOrgInfo orgInfo = new VwOrgInfo();

		    orgInfo.setAddress1(info.getAddress1());
		    orgInfo.setAddress2(info.getAddress2());
		    orgInfo.setCityId(info.getCityId());
		    orgInfo.setCityName(info.getCityName());
		    orgInfo.setCountryID(info.getCountryID());
		    orgInfo.setCountryName(info.getCountryName());
		    orgInfo.setDenomination(info.getDenomination());
		    orgInfo.setFacebookLink(info.getFacebookLink());
		    orgInfo.setGeneralInfo(info.getGeneralInfo());
		    orgInfo.setHours(info.getHours());
		    orgInfo.setOrganizationId(info.getOrganizationId());
		    orgInfo.setOrganizationName(info.getOrganizationName());
		    orgInfo.setOrgImage(info.getOrgImage());
		    orgInfo.setPastor1Bio(info.getPastor1Bio());
		    orgInfo.setPastor2Bio(info.getPastor2Bio());
		    orgInfo.setPhone(info.getPhone());
		    orgInfo.setPrimaryEmail(info.getPrimaryEmail());
		    orgInfo.setStateId(info.getStateId());
		    orgInfo.setStateName(info.getStateName());
		    orgInfo.setWebsite(info.getWebsite());
		    orgInfo.setZipCode(info.getZipCode());
		    orgInfo.setStripeAcctId(info.getStripeAcctId());
		    vwOrgInfos.add(orgInfo);
		 
		    
		}
		return vwOrgInfos;
	}

	@Override
	public int updateOrgInfo(VwOrganizationInfo orgInfo){
		
		StoredProcedureQuery proc = em.createNamedStoredProcedureQuery("OrganizationInfo.update_orgInfo");
		proc.setParameter("organizationId", orgInfo.getOrganizationId()).setParameter("website", orgInfo.getWebsite())
				.setParameter("primaryEmail", orgInfo.getPrimaryEmail()).setParameter("generalInfo", orgInfo.getGeneralInfo())
				.setParameter("hours", orgInfo.getHours()).setParameter("facebookLink", orgInfo.getFacebookLink())
				.setParameter("pastor1Bio", orgInfo.getPastor1Bio()).setParameter("pastor2Bio", orgInfo.getPastor2Bio());

		int result = proc.executeUpdate();

		return result;
	}

	private AmazonS3 getAmazonS3Client() {

		AWSCredentials credentials = new BasicAWSCredentials(awsAccessKeyId, awsAccessSecretKey);
		return new AmazonS3Client(credentials);
	}

	@Override
	public String getOrgImage(int orgId) {

		String orgImage = null;

		try {
			Query query = em
					.createNativeQuery(
							"select OrgImage returnvalue from organization where OrganizationId=:OrganizationId")
					.setParameter("OrganizationId", orgId);

			if (query.getSingleResult() != null) {
				orgImage = (String) query.getSingleResult();
			}
		} catch (NoResultException e) {

		}
		return orgImage;
	}

	@Override
	public int saveImage(int orgId, String imageName) {

		try {
			Query query = em
					.createNativeQuery(
							"update organization set OrgImage=:OrgImage where OrganizationId=:OrganizationId")
					.setParameter("OrgImage", imageName).setParameter("OrganizationId", orgId);

			int result = query.executeUpdate();
			return result;

		} catch (NoResultException e) {

		}
		return 0;
	}

	@Override
	public Upload uploadOrgFiles(InputStream fis, String imageName, String imgToDelete, int orgId) {

		Upload upload = null;

		try {

			AWSCredentials credentials = new BasicAWSCredentials(awsAccessKeyId, awsAccessSecretKey);
			AmazonS3 s3client = new AmazonS3Client(credentials);

			TransferManager manager = new TransferManager(credentials);
			ObjectMetadata meta = new ObjectMetadata();

			byte[] resultByte = IOUtils.toByteArray(fis);
			ByteArrayInputStream bis = new ByteArrayInputStream(resultByte);
			meta.setContentLength(resultByte.length);
			meta.setContentType(bis.toString());

			logger.info("length: {}", resultByte.length);
			String filePath = AppConstants.ORGANIZATION_FOLDER + AppConstants.SLASH + orgId + AppConstants.SLASH
					+ imageName;
			s3client.deleteObject(s3BucketName,
					AppConstants.ORGANIZATION_FOLDER + AppConstants.SLASH + orgId + AppConstants.SLASH + imgToDelete);

			upload = manager.upload(s3BucketName, filePath, bis, meta);
			s3client.setObjectAcl(s3BucketName, filePath, CannedAccessControlList.Private);

			if (upload.isDone() == false) {
				logger.info("Transfer: {}", upload.getDescription());
				logger.info("  - State: {}", upload.getState());
				logger.info("  - Transfer progress percentage: {}",
						upload.getProgress().getTotalBytesToTransfer() + "%");
				logger.info("  - Bytes Transfered: {}", upload.getProgress().getBytesTransferred());
				Thread.sleep(200);
			}
			upload.waitForCompletion();
			manager.shutdownNow();

		} catch (AmazonServiceException ase) {
			logger.error("Caught an AmazonServiceException, which " + "means your request made it "
					+ "to Amazon S3, but was rejected with an error response" + " for some reason.");
			logger.error("Error Message:    {}", ase.getMessage());
			logger.error("HTTP Status Code: {}", ase.getStatusCode());
			logger.error("AWS Error Code:   {}", ase.getErrorCode());
			logger.error("Error Type:       {}", ase.getErrorType());
			logger.error("Request ID:       {}", ase.getRequestId());
		} catch (AmazonClientException ace) {
			logger.error("Caught an AmazonClientException, which " + "means the client encountered "
					+ "an internal error while trying to " + "communicate with S3, "
					+ "such as not being able to access the network.");
			logger.error("Error Message: {}", ace.getMessage());
		} catch (IOException e) {
			logger.error("Error {}", e.getMessage());
		} catch (InterruptedException e) {
			logger.error("Error {}", e.getMessage());
		}
		return upload;
	}

	@Override
	public InputStream getOrgImageAsStream(int id) {

		String imageName = null;

		try {
			Query query = em
					.createNativeQuery(
							"select OrgImage returnvalue from organization where OrganizationId=:OrganizationId")
					.setParameter("OrganizationId", id);

			if (query.getSingleResult() != null) {
				imageName = (String) query.getSingleResult();
			}
		} catch (NoResultException e) {
		}

		String folderPath = null;
		if (imageName != null) {
			folderPath = AppConstants.ORGANIZATION_FOLDER + AppConstants.SLASH + id + AppConstants.SLASH + imageName;
		} else {
			folderPath = AppConstants.ORGANIZATION_FOLDER + AppConstants.SLASH + AppConstants.DEFAULT_ID
					+ AppConstants.SLASH + AppConstants.DEFAULT_ORG_IMAGE;
		}
		GetObjectRequest objectRequest = new GetObjectRequest(s3BucketName, folderPath);

		InputStream s3IStream = null;
		try {
			S3Object s3object = getAmazonS3Client().getObject(objectRequest);
			s3IStream = s3object.getObjectContent();

		} catch (AmazonServiceException ase) {
			logger.error("Caught an AmazonServiceException, which " + "means your request made it "
					+ "to Amazon S3, but was rejected with an error response" + " for some reason.");
			logger.error("Error Message:    {}", ase.getMessage());
			logger.error("HTTP Status Code: {}", ase.getStatusCode());
			logger.error("AWS Error Code:   {}", ase.getErrorCode());
			logger.error("Error Type:       {}", ase.getErrorType());
			logger.error("Request ID:       {}", ase.getRequestId());
			return null;
		} catch (AmazonClientException ace) {
			logger.error("Caught an AmazonClientException, which " + "means the client encountered "
					+ "an internal error while trying to " + "communicate with S3, "
					+ "such as not being able to access the network.");
			logger.error("Error Message: {}", ace.getMessage());
			return null;
		}
		return s3IStream;
	}
	
	 @Override
	 public int getLikeCount(int orgId) {
	  Integer likeCount = 0;
	  try {
	   Query likeQuery = em
	     .createNativeQuery(
	       "SELECT COUNT(*) AS likedSermons FROM `note` n, `NoteLike` nl WHERE n.`NoteId`=nl.`NoteId` AND n.`OrganizationId`=:organizationId AND `LikeCount`=1")
	     .setParameter("organizationId", orgId);

	   if (likeQuery.getSingleResult() != null) {
		  
	    likeCount = ((BigInteger) likeQuery.getSingleResult()).intValue();
	   }
	  } catch (NoResultException e) {

	  }
	  return likeCount;  
	 }


	 @Override
	 public int getDownloadCount(int orgId) {
	  Integer downloadCount = 0;
	  try {
	   Query downloadQuery = em
	     .createNativeQuery(
	       "SELECT COUNT(*) AS downloadedSermons FROM `note` n,`NoteDownload` nd WHERE n.`NoteId`=nd.`NoteId` AND n.`OrganizationId`=:organizationId")
	     .setParameter("organizationId", orgId);

	   if (downloadQuery.getSingleResult() != null) {
	    downloadCount = ((BigInteger) downloadQuery.getSingleResult()).intValue();
	   }
	  } catch (NoResultException e) {

	  }
	  return downloadCount;
	 }


	 @Override
	 public int getSermonCount(int orgId) {
	  Integer sermonCount = 0;
	  try {
	   Query sermonQuery = em
	     .createNativeQuery(
	       "SELECT COUNT(*) AS sermonCount FROM `note` WHERE `OrganizationId`=:organizationId AND `Published`='Y'")
	     .setParameter("organizationId", orgId);

	   if (sermonQuery.getSingleResult() != null) {
	    sermonCount = ((BigInteger) sermonQuery.getSingleResult()).intValue();
	   }
	  } catch (NoResultException e) {

	  }
	  return sermonCount;  

	 }
	 
}

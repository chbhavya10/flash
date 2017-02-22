package com.sermon.mynote.service.jpa;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.amazonaws.util.IOUtils;
import com.sermon.mynote.domain.VwOrganizationInfo;
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

	@Value("${amazon.link}")
	private String amazonLink;

	final Logger logger = LoggerFactory.getLogger(VwOrganizationInfoServiceImpl.class);

	@Override
	@Transactional(readOnly = true)
	public List<VwOrganizationInfo> findOrganizationInfoByOrgId(int orgId) {

		@SuppressWarnings("unchecked")
		TypedQuery<VwOrganizationInfo> query = (TypedQuery<VwOrganizationInfo>) em.createNativeQuery(
				"select o.* from  vw_organizationInfo o " + " WHERE (o.OrganizationId= :OrganizationId) ",
				VwOrganizationInfo.class).setParameter("OrganizationId", orgId);

		System.out.println(query.toString());

		List<VwOrganizationInfo> results = (List<VwOrganizationInfo>) query.getResultList();

		for (VwOrganizationInfo info : results) {

			String bucketName = s3BucketName + AppConstants.SLASH + orgImageBucketPath;
			String orgImgPath = null;
			String orgImg = info.getOrgImage();

			if (orgImg != null) {

				String s3Obj = info.getOrganizationId() + AppConstants.SLASH + orgImg;
				orgImgPath = generatePreSignedURL(bucketName, s3Obj);
				info.setOrgImage(orgImgPath);
			} else {
				String s3Obj = AppConstants.DEFAULT_ID + AppConstants.SLASH + AppConstants.DEFAULT_ORG_IMAGE;
				orgImgPath = generatePreSignedURL(bucketName, s3Obj);
				info.setOrgImage(orgImgPath);
			}
		}
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
}

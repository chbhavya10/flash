package com.sermon.mynote.service.jpa;

import java.net.URL;
import java.util.List;

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
import com.sermon.mynote.domain.VwNotedetails;
import com.sermon.mynote.repository.VwNotedetailsRepository;
import com.sermon.mynote.service.VwNotedetailsService;
import com.sermon.util.AppConstants;

@Service("vwNotedetailsService")
@Repository
@Transactional
public class VwNotedetailsServiceImpl implements VwNotedetailsService {

	@Autowired
	private VwNotedetailsRepository vwNotedetailsRepository;

	@Value("${s3.aws.access.key.id}")
	private String awsAccessKeyId;

	@Value("${s3.aws.access.secret.key}")
	private String awsAccessSecretKey;

	@Value("${s3.aws.bucket.name}")
	private String s3BucketName;

	@Value("${note.image.bucket.path}")
	private String noteImageBucketPath;

	@Value("${amazon.link}")
	private String amazonLink;

	@Transactional(readOnly = true)
	public List<VwNotedetails> findNoteDetailsByNoteId(int noteid) {

		List<VwNotedetails> notedetails = vwNotedetailsRepository.findNoteDetailsByNoteId(noteid);

		String bucketName = s3BucketName + AppConstants.SLASH + noteImageBucketPath;

		for (VwNotedetails vwNotedetails : notedetails) {

			String noteImgPath = null;
			String noteImg = vwNotedetails.getNoteImage();
			if (noteImg != null) {
				String s3Obj = vwNotedetails.getNoteId() + AppConstants.SLASH + noteImg;
				noteImgPath = generatePreSignedURL(bucketName, s3Obj);
				vwNotedetails.setNoteImage(noteImgPath);
			} else {
				String s3Obj = AppConstants.DEFAULT_ID + AppConstants.SLASH + AppConstants.DEFAULT_NOTE_IMAGE;
				noteImgPath = generatePreSignedURL(bucketName, s3Obj);
				vwNotedetails.setNoteImage(noteImgPath);
			}
		}
		return notedetails;
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

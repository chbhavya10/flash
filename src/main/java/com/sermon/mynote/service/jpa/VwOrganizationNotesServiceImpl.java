package com.sermon.mynote.service.jpa;

import java.net.URL;
import java.util.ArrayList;
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
import com.google.common.collect.Lists;
import com.sermon.mynote.domain.Note;
import com.sermon.mynote.domain.UserOrgList;
import com.sermon.mynote.domain.VwOrganizationNotes;
import com.sermon.mynote.domain.VwUserorganizations;
import com.sermon.mynote.repository.NoteRepository;
import com.sermon.mynote.repository.VwOrganizationNotesRepository;
import com.sermon.mynote.repository.VwUserorganizationsRepository;
import com.sermon.mynote.service.NoteService;
import com.sermon.mynote.service.VwOrganizationNotesService;
import com.sermon.util.AppConstants;

@Service("vwOrganizationNotesService")
@Repository
@Transactional
public class VwOrganizationNotesServiceImpl implements VwOrganizationNotesService {

	@Autowired
	private VwUserorganizationsRepository vwUserorganizationsRepository;
	
	@Autowired
	private VwOrganizationNotesRepository vwOrganizationNotesRepository;

	@Value("${s3.aws.bucket.name}")
	private String s3BucketName;

	@Value("${note.image.bucket.path}")
	private String noteImageBucketPath;

	@Value("${s3.aws.access.key.id}")
	private String awsAccessKeyId;

	@Value("${s3.aws.access.secret.key}")
	private String awsAccessSecretKey;
	
	@Autowired
	private NoteService noteService;
	
	@Autowired
	private NoteRepository noteRepository;

	@Transactional(readOnly = true)
	public List<VwOrganizationNotes> findAll() {

		List<VwOrganizationNotes> vwOrganizationNotes = (List<VwOrganizationNotes>) vwOrganizationNotesRepository
				.findAll();

		String bucketName = s3BucketName + AppConstants.SLASH + noteImageBucketPath;

		for (VwOrganizationNotes notes : vwOrganizationNotes) {

			String noteImgPath = null;
			String noteImg = notes.getNoteImage();

			if (noteImg != null) {

				String s3Obj = notes.getNoteId() + AppConstants.SLASH + noteImg;
				noteImgPath = noteService.generatePreSignedURL(bucketName, s3Obj);
				notes.setNoteImage(noteImgPath);
			} else {
				String s3Obj = AppConstants.DEFAULT_ID + AppConstants.SLASH + AppConstants.DEFAULT_NOTE_IMAGE;
				noteImgPath = noteService.generatePreSignedURL(bucketName, s3Obj);
				notes.setNoteImage(noteImgPath);
			}

		}
		return vwOrganizationNotes;
	}

	@Transactional(readOnly = true)
	public List<VwOrganizationNotes> findSermonsByOrgId(int orgid) {
		List<VwOrganizationNotes> vwOrganizationNotes = new ArrayList<VwOrganizationNotes>();
		
		try{

		vwOrganizationNotes = vwOrganizationNotesRepository.findSermonsByOrgId(orgid);

		String bucketName = s3BucketName + AppConstants.SLASH + noteImageBucketPath;

		for (VwOrganizationNotes notes : vwOrganizationNotes) {

			String noteImgPath = null;
			String noteImg = notes.getNoteImage();

			if (noteImg != null) {

				String s3Obj = notes.getNoteId() + AppConstants.SLASH + noteImg;
				noteImgPath = noteService.generatePreSignedURL(bucketName, s3Obj);
				notes.setNoteImage(noteImgPath);
			} else {
				String s3Obj = AppConstants.DEFAULT_ID + AppConstants.SLASH + AppConstants.DEFAULT_NOTE_IMAGE;
				noteImgPath = noteService.generatePreSignedURL(bucketName, s3Obj);
				notes.setNoteImage(noteImgPath);
			}
		}
		}catch(Exception e){
			
		}
		
		return vwOrganizationNotes;
	}

	@Override
	public List<VwOrganizationNotes> getUserFavorateChurchesNotes(int userId) {

		List<VwUserorganizations> orgList = Lists
				.newArrayList(vwUserorganizationsRepository.findOrganizationsByUser(userId));

		List<VwOrganizationNotes> userFavorateChurchSermons = new ArrayList<>();

		for (VwUserorganizations org : orgList) {
                      System.out.println(org.getorganizationId()+" "+orgList.size());
			List<VwOrganizationNotes> userFavorateChurchSermonsForOrganization = vwOrganizationNotesRepository
					.findSermonsByOrgId(org.getorganizationId());

			List<VwOrganizationNotes> favChurhSermons = parseObject(userFavorateChurchSermonsForOrganization);
			if (favChurhSermons != null
					&& favChurhSermons.size() > 0) {
				for (VwOrganizationNotes favorateChurchSermon : favChurhSermons) {
					System.out.println(favorateChurchSermon.getNoteId() +" "+favChurhSermons.size());
					Note note = noteRepository.findNoteByNoteId(favorateChurchSermon.getNoteId());
					String noteImagePath = note.getNoteImage();

					String bucketName = s3BucketName + AppConstants.SLASH + noteImageBucketPath;
					String noteImgPath = null;
					String noteImg = note.getNoteImage();
					if (noteImg != null) {
						String s3Obj = note.getNoteId() + AppConstants.SLASH + noteImg;
						noteImgPath = generatePreSignedURL(bucketName, s3Obj);
						favorateChurchSermon.setNoteImage(noteImgPath);
					} else {
						String s3Obj = AppConstants.DEFAULT_ID + AppConstants.SLASH + AppConstants.DEFAULT_NOTE_IMAGE;
						noteImgPath = generatePreSignedURL(bucketName, s3Obj);
						favorateChurchSermon.setNoteImage(noteImgPath);

					}
					System.out.println(favorateChurchSermon.getNoteImage());
					userFavorateChurchSermons.add(favorateChurchSermon);
				}
			}
		}
		return userFavorateChurchSermons;
	}

	private List<VwOrganizationNotes> parseObject(List<VwOrganizationNotes> userFavorateChurchSermonsForOrganization) {
		
		List<VwOrganizationNotes> userFavorateChurchSermons = new ArrayList<VwOrganizationNotes>(); 
		for(int i = 0 ; i< userFavorateChurchSermonsForOrganization.size();i++){
			VwOrganizationNotes organization = new VwOrganizationNotes();
			organization.setAuthor(userFavorateChurchSermonsForOrganization.get(i).getAuthor());
			organization.setEventDate(userFavorateChurchSermonsForOrganization.get(i).getEventDate());
			organization.setEventTime(userFavorateChurchSermonsForOrganization.get(i).getEventTime());
			organization.setIntroduction(userFavorateChurchSermonsForOrganization.get(i).getIntroduction());
			organization.setNoteId(userFavorateChurchSermonsForOrganization.get(i).getNoteId());
			organization.setNoteImage(userFavorateChurchSermonsForOrganization.get(i).getNoteImage());
			organization.setOrganizationid(userFavorateChurchSermonsForOrganization.get(i).getOrganizationid());
			organization.setOrganizationName(userFavorateChurchSermonsForOrganization.get(i).getOrganizationName());
			organization.setPublished(userFavorateChurchSermonsForOrganization.get(i).getPublished());
			organization.setSubTitle(userFavorateChurchSermonsForOrganization.get(i).getSubTitle());
			organization.setTitle(userFavorateChurchSermonsForOrganization.get(i).getTitle());
			
			userFavorateChurchSermons.add(organization);
		}
		
		return userFavorateChurchSermons;
		
	}

	public String generatePreSignedURL(String bucketName, String objectKey) {

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

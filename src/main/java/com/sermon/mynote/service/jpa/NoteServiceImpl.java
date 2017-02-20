package com.sermon.mynote.service.jpa;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.google.common.collect.Lists;
import com.sermon.mynote.domain.AddNote;
import com.sermon.mynote.domain.AddSection;
import com.sermon.mynote.domain.AddSubSection;
import com.sermon.mynote.domain.Note;
import com.sermon.mynote.domain.PublishSchedule;
import com.sermon.mynote.domain.Section;
import com.sermon.mynote.domain.SubSection;
import com.sermon.mynote.repository.NoteRepository;
import com.sermon.mynote.repository.PublishRepository;
import com.sermon.mynote.repository.SectionRepository;
import com.sermon.mynote.repository.SubSectionRepository;
import com.sermon.mynote.service.NoteService;
import com.sermon.util.AppConstants;

@Service("noteService")
@Repository
@Transactional
public class NoteServiceImpl implements NoteService {

	final Logger logger = LoggerFactory.getLogger(NoteServiceImpl.class);

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private PublishRepository publishRepository;

	@Autowired
	private SectionRepository sectionRepository;

	@Autowired
	private SubSectionRepository subsectionRepository;

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

	int noteId;
	int sectionId;
	int subSectionId;

	public Note findById(Integer id) {
		return noteRepository.findOne(id.intValue());
	}

	public Note save(Note note) {

		/*
		 * Date date = new Date(); note.setEventDate(new
		 * Timestamp(date.getTime())); note.setEventTime(new
		 * Timestamp(date.getTime()));
		 */

		return noteRepository.save(note);
	}

	/*
	 * 
	 * public void createNote(Integer authorId,String title,String
	 * subTitle,String introduction, Date eventDate,Time eventTime,Integer
	 * noteCategoryId,String keywords,Integer organizationId);
	 */
	@Override
	public int delete(Note note) {
		/*
		 * Note mergedNote = em.merge(note); em.remove(mergedNote); logger.info(
		 * "Note with id: " + note.getNoteId()+ " deleted successfully");
		 */

		StoredProcedureQuery proc = em.createNamedStoredProcedureQuery("Note.delete_note");

		proc.setParameter("NoteId", note.getNoteId());
		int result = proc.executeUpdate();

		return result;
	}

	@Transactional(readOnly = true)
	public List<Note> findNotesByAuthorId(int authorId) {

		return Lists.newArrayList(noteRepository.findNotesByAuthorId(authorId));
	}

	@Override
	public PublishSchedule publishLater(PublishSchedule publishSchedule) {
		return publishRepository.save(publishSchedule);
	}

	@Override
	public int updatePublishNow(Integer noteId, String published) {

		StoredProcedureQuery proc = em.createNamedStoredProcedureQuery("Note.update_publish_now");
		proc.setParameter("noteId", noteId).setParameter("published", published);

		int result = proc.executeUpdate();
		return result;

	}

	@Override
	@Transactional(readOnly = false)
	public int createNote(AddNote addNote) {

		noteId = addNote.getNoteId();

		Note note = new Note();

		note.setAuthorId(addNote.getAuthorId());
		note.setCategoryId(addNote.getCategoryId());
		/*
		 * Date date = new Date(); note.setEventDate(new
		 * Timestamp(date.getTime())); note.setEventTime(new
		 * Timestamp(date.getTime()));
		 */
		note.setEventDate(addNote.getEventDate());
		note.setEventTime(addNote.getEventTime());
		note.setGroupId(addNote.getGroupId());
		note.setIntroduction(addNote.getIntroduction());
		List<String> keywords = addNote.getKeywords();
		String sectionKeyword = StringUtils.join(keywords, ',');
		note.setKeywords(sectionKeyword);
		note.setOrganizationId(addNote.getOrganizationId());
		note.setPublished(addNote.getPublished());
		note.setSubTitle(addNote.getSubTitle());
		note.setTitle((addNote.getTitle()).trim().replaceAll("\\s+", " "));
		note.setPreacherName(addNote.getPreacherName());

		Note newNote = noteRepository.save(note);

		List<AddSection> sections = addNote.getSections();

		if (sections != null && !sections.isEmpty()) {
			for (AddSection section : sections) {
				if (noteId == section.getNoteId()) {

					sectionId = section.getSectionId();

					Section noteSection = new Section();

					noteSection.setNoteId(newNote.getNoteId());
					List<Integer> sectionKeywords = section.getSectionKeyWords();
					String newSectionKeyword = StringUtils.join(sectionKeywords, ',');
					noteSection.setSectionKeyWords(newSectionKeyword);
					System.out.println(section.getSectionText());
					noteSection.setSectionText((section.getSectionText()).trim().replaceAll("\\s+", " "));
					System.out.println(noteSection.getSectionText());
					Section newSection = sectionRepository.save(noteSection);

					List<AddSubSection> subSections = addNote.getSubSections();

					if (subSections != null && !subSections.isEmpty()) {
						for (AddSubSection subSection : subSections) {
							if (sectionId == subSection.getSectionId()) {

								SubSection noteSubSection = new SubSection();

								noteSubSection.setSectionId(newSection.getSectionId());
								List<Integer> subSectionKeywords = subSection.getSubsectionKeyWords();
								String newSubSectionKeyword = StringUtils.join(subSectionKeywords, ',');
								noteSubSection.setSubsectionKeyWords(newSubSectionKeyword);
								noteSubSection.setSubsectionText(
										(subSection.getSubsectionText()).trim().replaceAll("\\s+", " "));

								subsectionRepository.save(noteSubSection);
							}
						}
					}
				}
			}
		}

		return newNote.getNoteId();
	}

	@Override
	public AddNote getNote(int id) {

		Note note = noteRepository.findOne(id);
		List<Section> sections = new ArrayList<Section>();
		List<SubSection> subSections = new ArrayList<SubSection>();
		List<SubSection> tempSubSections = new ArrayList<SubSection>();
		sections = sectionRepository.findSectionByNoteId(note.getNoteId());
		for (Section section : sections) {
			tempSubSections = subsectionRepository.findSubsectionBySectionId(section.getSectionId());
			subSections.addAll(tempSubSections);
		}

		AddNote addNote = new AddNote();
		addNote.setAuthorId(note.getAuthorId());
		addNote.setCategoryId(note.getCategoryId());
		addNote.setEventDate(note.getEventDate());
		addNote.setEventTime(note.getEventTime());
		addNote.setGroupId(note.getGroupId());
		addNote.setIntroduction(note.getIntroduction());
		List<String> keywords = new ArrayList<String>(Arrays.asList(note.getKeywords().split(",")));
		addNote.setKeywords(keywords);
		addNote.setNoteId(note.getNoteId());
		addNote.setOrganizationId(note.getOrganizationId());
		addNote.setPublished(note.getPublished());
		addNote.setSubTitle(note.getSubTitle());
		addNote.setTitle(note.getTitle());
		addNote.setPreacherName(note.getPreacherName());

		String orgName = getOrganizationName(note.getOrganizationId());
		addNote.setOrganizationName(orgName);

		String bucketName = s3BucketName + AppConstants.SLASH + noteImageBucketPath;
		String noteImgPath = null;
		String noteImg = note.getNoteImage();
		if (noteImg != null) {
			String s3Obj = note.getNoteId() + AppConstants.SLASH + noteImg;
			noteImgPath = generatePreSignedURL(bucketName, s3Obj);
			addNote.setNoteImage(noteImgPath);
		}

		List<AddSection> addSections = new ArrayList<AddSection>();

		for (Section section : sections) {

			AddSection addSection = new AddSection();
			addSection.setNoteId(section.getNoteId());
			addSection.setSectionId(section.getSectionId());
			List<String> sectionKeyWords = null;
			List<Integer> list = new ArrayList<Integer>();
			if (section.getSectionKeyWords() != null) {

				sectionKeyWords = new ArrayList<String>(Arrays.asList(section.getSectionKeyWords().split(",")));

				for (String s : sectionKeyWords) {
					try {
						list.add(Integer.valueOf(s));
					} catch (NumberFormatException e) {
						continue;
					}
				}
			}
			addSection.setSectionKeyWords(list);
			addSection.setSectionText(section.getSectionText());
			addSections.add(addSection);
		}

		addNote.setSections(addSections);

		List<AddSubSection> addSubSections = new ArrayList<AddSubSection>();

		for (SubSection subSection : subSections) {

			AddSubSection addSubSection = new AddSubSection();
			addSubSection.setSectionId(subSection.getSectionId());
			addSubSection.setSubsectionId(subSection.getSubsectionId());
			List<String> subSectionKeyWords = null;
			List<Integer> list = new ArrayList<Integer>();

			if (subSection.getSubsectionKeyWords() != null) {
				subSectionKeyWords = new ArrayList<String>(
						Arrays.asList(subSection.getSubsectionKeyWords().split(",")));

				for (String s : subSectionKeyWords) {
					try {
						list.add(Integer.valueOf(s));
					} catch (NumberFormatException ex) {
						continue;
					}
				}
			}
			addSubSection.setSubsectionKeyWords(list);
			addSubSection.setSubsectionText(subSection.getSubsectionText());
			addSubSections.add(addSubSection);
		}

		addNote.setSubSections(addSubSections);

		return addNote;
	}

	@Override
	public int updatePublish(PublishSchedule publishSchedule) {

		StoredProcedureQuery proc = em.createNamedStoredProcedureQuery("PublishSchedule.update_publish_schedule");
		proc.setParameter("noteId", publishSchedule.getNoteId())
				.setParameter("publishDate", publishSchedule.getPublishDate())
				.setParameter("publishTime", publishSchedule.getPublishTime());

		int result = proc.executeUpdate();
		return result;
	}

	@Override
	public Upload upLoadNoteFiles(InputStream inputStream, String imgName, String imgToDelete, int noteId) {
		Upload upload = null;

		try {

			AWSCredentials credentials = new BasicAWSCredentials(awsAccessKeyId, awsAccessSecretKey);
			AmazonS3 s3client = new AmazonS3Client(credentials);

			TransferManager manager = new TransferManager(credentials);
			ObjectMetadata meta = new ObjectMetadata();

			byte[] resultByte = IOUtils.toByteArray(inputStream);
			ByteArrayInputStream bis = new ByteArrayInputStream(resultByte);
			meta.setContentLength(resultByte.length);
			meta.setContentType(bis.toString());

			logger.info("length: {}", resultByte.length);
			String filePath = AppConstants.NOTES_FOLDER + AppConstants.SLASH + noteId + AppConstants.SLASH + imgName;
			s3client.deleteObject(s3BucketName,
					AppConstants.NOTES_FOLDER + AppConstants.SLASH + noteId + AppConstants.SLASH + imgToDelete);

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
	public String getNoteImage(int noteId) {
		String noteImage = null;

		try {
			Query query = em.createNativeQuery("select noteImage returnvalue from note where NoteId=:NoteId")
					.setParameter("NoteId", noteId);

			if (query.getSingleResult() != null) {
				noteImage = (String) query.getSingleResult();
			}
		} catch (NoResultException e) {

		}
		return noteImage;
	}

	@Override
	public int saveImage(int noteId, String docName) {
		try {
			Query query = em.createNativeQuery("update note set noteImage=:noteImage where NoteId=:noteId")
					.setParameter("noteImage", docName).setParameter("noteId", noteId);

			int result = query.executeUpdate();
			return result;

		} catch (NoResultException e) {

		}
		return 0;

	}

	private String generatePreSignedURL(String bucketName, String objectKey) {

		java.util.Date expiration = new java.util.Date();
		long milliSeconds = expiration.getTime();
		milliSeconds += 100 * 24 * 60 * 60;
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

	private String getOrganizationName(int orgId) {
		String organizationName = null;

		try {
			Query query = em
					.createNativeQuery(
							"select OrganizationName returnvalue from organization where OrganizationId=:OrganizationId")
					.setParameter("OrganizationId", orgId);

			if (query.getSingleResult() != null) {
				organizationName = (String) query.getSingleResult();
			}
		} catch (NoResultException e) {

		}
		return organizationName;
	}

	@Override
	public InputStream getUserDocumentAsStream(int noteId) throws AmazonClientException, AmazonServiceException {

		String imageName = null;

		try {
			Query query = em.createNativeQuery("select noteImage returnvalue from note where NoteId=:noteId")
					.setParameter("noteId", noteId);

			if (query.getSingleResult() != null) {
				imageName = (String) query.getSingleResult();
			}
		} catch (NoResultException e) {

		}

		String folderPath = AppConstants.NOTES_FOLDER + AppConstants.SLASH + noteId + AppConstants.SLASH + imageName;
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
	public Upload upLoadFiles(InputStream inputStream, String imgName, String imgToDelete, int noteId, int orgId) {
		Upload upload = null;

		try {

			AWSCredentials credentials = new BasicAWSCredentials(awsAccessKeyId, awsAccessSecretKey);
			AmazonS3 s3client = new AmazonS3Client(credentials);

			TransferManager manager = new TransferManager(credentials);
			ObjectMetadata meta = new ObjectMetadata();

			byte[] resultByte = IOUtils.toByteArray(inputStream);
			ByteArrayInputStream bis = new ByteArrayInputStream(resultByte);
			meta.setContentLength(resultByte.length);
			meta.setContentType(bis.toString());

			logger.info("length: {}", resultByte.length);

			String filePath = null;

			if (noteId > 0) {
				filePath = AppConstants.NOTES_FOLDER + AppConstants.SLASH + noteId + AppConstants.SLASH + imgName;

				s3client.deleteObject(s3BucketName,
						AppConstants.NOTES_FOLDER + AppConstants.SLASH + noteId + AppConstants.SLASH + imgToDelete);
			} else if (orgId > 0) {
				filePath = AppConstants.ORGANIZATION_FOLDER + AppConstants.SLASH + orgId + AppConstants.SLASH + imgName;

				s3client.deleteObject(s3BucketName, AppConstants.ORGANIZATION_FOLDER + AppConstants.SLASH + orgId
						+ AppConstants.SLASH + imgToDelete);
			}

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
	public int deleteImage(int noteId) {

		try {

			AWSCredentials credentials = new BasicAWSCredentials(awsAccessKeyId, awsAccessSecretKey);
			AmazonS3 s3client = new AmazonS3Client(credentials);

			TransferManager manager = new TransferManager(credentials);

			String imageName = null;

			try {
				Query query = em.createNativeQuery("select noteImage returnvalue from note where NoteId=:noteId")
						.setParameter("noteId", noteId);

				if (query.getSingleResult() != null) {
					imageName = (String) query.getSingleResult();
				}
			} catch (NoResultException e) {

			}

			if (noteId > 0) {

				s3client.deleteObject(s3BucketName,
						AppConstants.NOTES_FOLDER + AppConstants.SLASH + noteId + AppConstants.SLASH + imageName);
			}

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
		}

		String docName = null;
		saveImage(noteId, docName);

		return 0;
	}

}

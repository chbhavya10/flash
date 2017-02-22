package com.sermon.mynote.service.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sermon.mynote.domain.VwOrganizationNotes;
import com.sermon.mynote.repository.VwOrganizationNotesRepository;
import com.sermon.mynote.service.NoteService;
import com.sermon.mynote.service.VwOrganizationNotesService;
import com.sermon.util.AppConstants;

@Service("vwOrganizationNotesService")
@Repository
@Transactional
public class VwOrganizationNotesServiceImpl implements VwOrganizationNotesService {

	@Autowired
	private VwOrganizationNotesRepository vwOrganizationNotesRepository;

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

	@Autowired
	private NoteService noteService;

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

		List<VwOrganizationNotes> vwOrganizationNotes = vwOrganizationNotesRepository.findSermonsByOrgId(orgid);

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

}

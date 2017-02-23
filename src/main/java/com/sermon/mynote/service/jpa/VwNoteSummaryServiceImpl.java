package com.sermon.mynote.service.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sermon.mynote.domain.VwNoteSummary;
import com.sermon.mynote.repository.VwNoteSummaryRepository;
import com.sermon.mynote.service.NoteService;
import com.sermon.mynote.service.VwNoteSummaryService;
import com.sermon.util.AppConstants;

@Service("vwNoteSummaryService")
@Repository
@Transactional
public class VwNoteSummaryServiceImpl implements VwNoteSummaryService {

	@Autowired
	private VwNoteSummaryRepository vwNoteSummaryRepository;

	@Value("${s3.aws.bucket.name}")
	private String s3BucketName;

	@Value("${note.image.bucket.path}")
	private String noteImageBucketPath;

	@Autowired
	private NoteService noteService;

	@Transactional(readOnly = true)
	public List<VwNoteSummary> findAll() {

		List<VwNoteSummary> noteSummaries = (List<VwNoteSummary>) vwNoteSummaryRepository.findAll();

		String bucketName = s3BucketName + AppConstants.SLASH + noteImageBucketPath;
		for (VwNoteSummary summary : noteSummaries) {

			String noteImgPath = null;
			String noteImg = summary.getNoteImage();

			if (noteImg != null) {

				String s3Obj = summary.getNoteId() + AppConstants.SLASH + noteImg;
				noteImgPath = noteService.generatePreSignedURL(bucketName, s3Obj);
				summary.setNoteImage(noteImgPath);
			} else {

				String s3Obj = AppConstants.DEFAULT_ID + AppConstants.SLASH + AppConstants.DEFAULT_NOTE_IMAGE;
				noteImgPath = noteService.generatePreSignedURL(bucketName, s3Obj);
				summary.setNoteImage(noteImgPath);
			}
		}
		return noteSummaries;
	}

	@Transactional(readOnly = true)
	public List<VwNoteSummary> findNoteSummaryByNoteId(int noteid) {

		List<VwNoteSummary> noteSummaries = vwNoteSummaryRepository.findNoteSummaryByNoteId(noteid);

		String bucketName = s3BucketName + AppConstants.SLASH + noteImageBucketPath;
		for (VwNoteSummary summary : noteSummaries) {

			String noteImgPath = null;
			String noteImg = summary.getNoteImage();

			if (noteImg != null) {

				String s3Obj = summary.getNoteId() + AppConstants.SLASH + noteImg;
				noteImgPath = noteService.generatePreSignedURL(bucketName, s3Obj);
				summary.setNoteImage(noteImgPath);
			} else {

				String s3Obj = AppConstants.DEFAULT_ID + AppConstants.SLASH + AppConstants.DEFAULT_NOTE_IMAGE;
				noteImgPath = noteService.generatePreSignedURL(bucketName, s3Obj);
				summary.setNoteImage(noteImgPath);
			}
		}
		return noteSummaries;
	}
}

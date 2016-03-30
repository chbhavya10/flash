package com.sermon.mynote.service.jpa;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sermon.mynote.domain.NoteDownload;
import com.sermon.mynote.repository.NoteDownloadRepository;
import com.sermon.mynote.service.NoteDownloadService;

@Service("noteDownloadService")
@Repository
@Transactional
public class NoteDownloadServiceImpl implements NoteDownloadService {

	@Autowired
	private NoteDownloadRepository noteDownloadRepository;

	@Override
	public NoteDownload save(NoteDownload noteDownload) {

		Date date = new Date(System.currentTimeMillis());
		Time time = new Time(System.currentTimeMillis());

		String myDate = date + " " + time;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date utilDate = new java.util.Date();
		try {
			utilDate = sdf.parse(myDate);
		} catch (ParseException e) {
		}

		DateTime dateTime = new DateTime(utilDate);
		noteDownload.setDownloadDate(dateTime);

		return noteDownloadRepository.save(noteDownload);
	}
}
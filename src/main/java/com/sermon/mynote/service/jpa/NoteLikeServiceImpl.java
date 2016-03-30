package com.sermon.mynote.service.jpa;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sermon.mynote.domain.NoteLike;
import com.sermon.mynote.repository.NoteLikeRepository;
import com.sermon.mynote.service.NoteLikeService;

@Service("noteLikeService")
@Repository
@Transactional
public class NoteLikeServiceImpl implements NoteLikeService {

	final Logger logger = LoggerFactory.getLogger(NoteServiceImpl.class);

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private NoteLikeRepository noteLikeRepository;

	@Override
	public NoteLike save(NoteLike noteLike) {
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
		noteLike.setLikeDate(dateTime);

		return noteLikeRepository.save(noteLike);
	}

	@Override
	public int updateLike(int noteId, int userId, int likeCount) {

		StoredProcedureQuery proc = em.createNamedStoredProcedureQuery("NoteLike.update_like");
		proc.setParameter("noteId", noteId).setParameter("userId", userId).setParameter("likeCount", likeCount);

		int result = proc.executeUpdate();
		return result;

	}

}

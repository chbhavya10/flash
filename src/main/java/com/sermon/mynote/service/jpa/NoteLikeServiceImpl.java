package com.sermon.mynote.service.jpa;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

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

		Date date = new Date();
		Timestamp dateTime = new Timestamp(date.getTime());
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

	@Transactional(readOnly = true)
	public int findById(int noteId, int userId) {
		try {
			Query query = em
					.createNativeQuery(
							"select LikeCount returnvalue from NoteLike where NoteId=:NoteId and UserId=:UserId")
					.setParameter("NoteId", noteId).setParameter("UserId", userId);
			System.out.println(query);
			// query.setParameter("username", username);

			if (query.getSingleResult() != null) {
				return (Integer) query.getSingleResult();
			}
		} catch (NoResultException e) {

		}
		return 0;
	}

}

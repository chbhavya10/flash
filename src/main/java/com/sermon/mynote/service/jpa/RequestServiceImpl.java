package com.sermon.mynote.service.jpa;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.sermon.mynote.domain.Request;
import com.sermon.mynote.domain.RequestType;
import com.sermon.mynote.repository.RequestRepository;
import com.sermon.mynote.repository.RequestTypeRepository;
import com.sermon.mynote.service.RequestService;
import com.sermon.util.AppConstants;

@Service("requestService")
@Repository
@Transactional
public class RequestServiceImpl implements RequestService {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private RequestTypeRepository requestTypeRepository;

	@Autowired
	private RequestRepository requestRepository;

	@Override
	public List<RequestType> findAll() {
		return Lists.newArrayList(requestTypeRepository.findAll());
	}

	@Override
	public Request save(Request request) {

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
		request.setRequestDate(dateTime);
		request.setRequestStatus(AppConstants.REQUESTED);

		return requestRepository.save(request);

	}

	@Override
	public Request findById(int id) {
		return requestRepository.findOne(id);
	}

	@Override
	public int updateRequest(int requestId, String requestUpdate, String requestStatus) {

		StoredProcedureQuery proc = em.createNamedStoredProcedureQuery("Request.update_request");
		proc.setParameter("requestId", requestId).setParameter("requestUpdate", requestUpdate)
				.setParameter("requestStatus", requestStatus);

		int result = proc.executeUpdate();
		return result;
	}

}

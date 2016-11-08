package com.sermon.mynote.service.jpa;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.sermon.mynote.domain.Request;
import com.sermon.mynote.domain.RequestDetails;
import com.sermon.mynote.domain.RequestType;
import com.sermon.mynote.domain.User;
import com.sermon.mynote.repository.RequestRepository;
import com.sermon.mynote.repository.RequestTypeRepository;
import com.sermon.mynote.service.RequestService;
import com.sermon.mynote.service.UserService;
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

	@Autowired
	private UserService userService;

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
	public RequestDetails findById(int id) {

		RequestDetails details = new RequestDetails();

		Request request = requestRepository.findOne(id);

		User user = userService.findById(request.getUserId());

		String requestType = null;
		try {
			Query query = em
					.createNativeQuery(
							"select RequestType returnvalue from RequestType where RequestTypeID=:RequestTypeID")
					.setParameter("RequestTypeID", request.getRequestTypeId());

			if (query.getSingleResult() != null) {
				requestType = (String) query.getSingleResult();
			}
		} catch (NoResultException e) {

		}
		
		String organizationName=null;
		try {
			Query query = em
					.createNativeQuery(
							"select OrganizationName returnvalue from organization where OrganizationId=:OrganizationId")
					.setParameter("OrganizationId", request.getOrganizationId());

			if (query.getSingleResult() != null) {
				organizationName = (String) query.getSingleResult();
			}
		} catch (NoResultException e) {

		}

		details.setRequestType(requestType);
		details.setIsPrivate(request.getIsPrivate());
		details.setOrganizationId(request.getOrganizationId());
		details.setOrganizationName(organizationName);
		details.setRequestDate(request.getRequestDate());
		details.setRequestedBy(user.getUserName());
		details.setRequestEmail(request.getRequestEmail());
		details.setRequestId(request.getRequestId());
		details.setRequestNotes(request.getRequestNotes());
		details.setRequestPhone(request.getRequestPhone());
		details.setRequestStatus(request.getRequestStatus());
		details.setRequestTypeId(request.getRequestTypeId());
		details.setRequestUpdate(request.getRequestUpdate());
		details.setUserEmail(user.getUserEmail());
		details.setUserId(request.getUserId());
		details.setUserPhone(user.getUserMobile());

		return details;
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

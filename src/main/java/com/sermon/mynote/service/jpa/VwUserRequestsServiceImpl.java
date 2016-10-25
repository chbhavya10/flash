package com.sermon.mynote.service.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sermon.mynote.domain.VwUserRequests;
import com.sermon.mynote.repository.VwUserRequestsRepository;
import com.sermon.mynote.service.VwUserRequestsService;

@Service("vwUserRequestsService")
@Repository
@Transactional
public class VwUserRequestsServiceImpl implements VwUserRequestsService {

	@Autowired
	private VwUserRequestsRepository vwUserRequestsRepository;

	@Override
	public List<VwUserRequests> findRequestsByUserId(int userId) {

		return vwUserRequestsRepository.findRequestsByUserId(userId);
	}

	@Override
	public List<VwUserRequests> findRequestsByOrgId(int orgId) {

		return vwUserRequestsRepository.findRequestsByOrgId(orgId);
	}

}

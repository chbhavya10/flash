package com.sermon.mynote.service.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.sermon.mynote.domain.RequestType;
import com.sermon.mynote.repository.RequestTypeRepository;
import com.sermon.mynote.service.RequestService;

@Service("requestService")
@Repository
@Transactional
public class RequestServiceImpl implements RequestService {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private RequestTypeRepository requestTypeRepository;

	@Override
	public List<RequestType> findAll() {
		return Lists.newArrayList(requestTypeRepository.findAll());
	}

}

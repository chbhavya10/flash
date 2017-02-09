package com.sermon.mynote.service.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.sermon.mynote.domain.Denomination;
import com.sermon.mynote.repository.DenominationRepository;
import com.sermon.mynote.service.DenominationService;

@Service("denominationService")
@Repository
@Transactional
public class DenominationServiceImpl implements DenominationService {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private DenominationRepository denominationRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Denomination> findAll() {
		return Lists.newArrayList(denominationRepository.findAll());
	}

}

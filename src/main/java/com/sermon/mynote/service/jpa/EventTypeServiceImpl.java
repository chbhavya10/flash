package com.sermon.mynote.service.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.sermon.mynote.domain.EventType;
import com.sermon.mynote.repository.EventTypeRepository;
import com.sermon.mynote.service.EventTypeService;

@Service("eventTypeService")
@Repository
@Transactional
public class EventTypeServiceImpl implements EventTypeService {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private EventTypeRepository eventTypeRepository;

	@Override
	public List<EventType> findAll() {
		return Lists.newArrayList(eventTypeRepository.findAll());
	}

}

package com.sermon.mynote.service.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.sermon.mynote.domain.Organization;
import com.sermon.mynote.domain.VwUserorganizations;
import com.sermon.mynote.repository.VwUserorganizationsRepository;
import com.sermon.mynote.service.VwUserorganizationsService;

@Service("vworganizationService")
@Repository
@Transactional
public class VwUserorganizationsServiceImpl implements VwUserorganizationsService {
	
	@Autowired
	private VwUserorganizationsRepository vwUserorganizationsRepository;
	
	
	@Transactional(readOnly=true)
	public List<VwUserorganizations> findOrganizationsByUser(int userid){
		
		return Lists.newArrayList(vwUserorganizationsRepository.findOrganizationsByUser(userid));
		
	}

}

package com.sermon.mynote.service.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sermon.mynote.domain.SubSection;
import com.sermon.mynote.repository.SubSectionRepository;
import com.sermon.mynote.service.SubSectionService;

@Service("subsectionService")
@Repository
@Transactional
public class SubSectionServiceImpl implements SubSectionService{

	
	final Logger logger = LoggerFactory.getLogger(SubSectionServiceImpl.class);	

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private SubSectionRepository subsectionRepository;
	
	
   public SubSection findById(Integer id)
   {
	   return subsectionRepository.findOne(id.intValue());
   }
	
	public SubSection save(SubSection subsection){
		
		return subsectionRepository.save(subsection);
	}
	

	@Override
    public void delete(SubSection subsection) {
		SubSection mergedsubSection = em.merge(subsection);
        em.remove(mergedsubSection);
    }
}

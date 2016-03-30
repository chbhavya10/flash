package com.sermon.mynote.service.jpa;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sermon.mynote.domain.Section;
import com.sermon.mynote.repository.SectionRepository;
import com.sermon.mynote.service.SectionService;



@Service("sectionService")
@Repository
@Transactional
public class SectionServiceImpl implements SectionService{
	
	
	final Logger logger = LoggerFactory.getLogger(SectionServiceImpl.class);	

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private SectionRepository sectionRepository;
	
	
   public Section findById(Integer id)
   {
	   return sectionRepository.findOne(id.intValue());
   }
	
	public Section save(Section section){
		
		return sectionRepository.save(section);
	}
	

	@Override
	public int delete(Section section) {
		
		/*Section mergedSection = em.merge(section);
		em.remove(mergedSection);*/
		
		StoredProcedureQuery proc = em
				.createNamedStoredProcedureQuery("Section.delete_section");

		proc.setParameter("SectionID", section.getSectionId());

		 int result=proc.executeUpdate();

		return result;
    }
	

}

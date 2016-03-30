package com.sermon.mynote.service.jpa;



import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.sermon.mynote.domain.UserFavorite;
import com.sermon.mynote.domain.VwNotedetails;
import com.sermon.mynote.repository.UserFavoriteRepository;
import com.sermon.mynote.service.UserFavoriteService;
import com.sermon.mynote.web.controller.UserFavController;

@Service("userFavoriteService")
@Repository
@Transactional
public class UserFavoriteImpl implements UserFavoriteService{
	

	 final Logger logger = LoggerFactory.getLogger(UserFavoriteImpl.class);	
	
	@PersistenceContext
    private EntityManager em;
	
	@Autowired
	private UserFavoriteRepository userFavoriteRepository;
	
	public UserFavorite save(UserFavorite userFavorite)
	{
		return userFavoriteRepository.save(userFavorite);
	}

	@Transactional(readOnly=true)
	public UserFavorite findById(Integer id) {
		return userFavoriteRepository.findOne(id);
	}
		
	
	
	@Transactional(readOnly=true)
	public List<UserFavorite> findUserFavByUserId(int userid) 
	{
		return Lists.newArrayList(userFavoriteRepository.findUserFavByUserId(userid));
		
	}
	
	
	
	@Override
    public void delete(UserFavorite userFavorite) {
		UserFavorite mergedContact = em.merge(userFavorite);
        em.remove(mergedContact);
        logger.info("User Favorite with id: " + userFavorite.getUserFavoriteId() + " deleted successfully");
    }

	
}

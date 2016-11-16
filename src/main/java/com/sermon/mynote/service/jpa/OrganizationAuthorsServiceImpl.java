package com.sermon.mynote.service.jpa;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sermon.mynote.domain.OrganizationAuthors;
import com.sermon.mynote.repository.OrganizationAuthorsRepository;
import com.sermon.mynote.service.OrganizationAuthorsService;

@Service("organizationAuthorService")
@Repository
@Transactional
public class OrganizationAuthorsServiceImpl implements OrganizationAuthorsService {

	@Autowired
	private OrganizationAuthorsRepository organizationAuthorRepository;

	@PersistenceContext
	private EntityManager em;

	@Override
	public OrganizationAuthors save(OrganizationAuthors authors) {

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
		authors.setCreateDate(dateTime);
		authors.setModifyDate(dateTime);
		authors.setStatus("ACTIVE");

		return organizationAuthorRepository.save(authors);
	}

}

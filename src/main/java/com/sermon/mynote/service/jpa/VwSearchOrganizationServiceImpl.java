package com.sermon.mynote.service.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sermon.mynote.domain.SearchOrganization;
import com.sermon.mynote.repository.VwSearchOrganizationRepository;
import com.sermon.mynote.service.VwSearchOrganizationService;


@Service("vwSearchOrganizationService")
@Repository
@Transactional
public class VwSearchOrganizationServiceImpl implements  VwSearchOrganizationService{
	
	
	
	@Autowired
	private VwSearchOrganizationRepository vwSearchOrganizationRepository;
	

	@PersistenceContext
    private EntityManager em;
	

	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<SearchOrganization> SearchOrganiz(String organizationName,String zipCode,String city)
	{

		TypedQuery<SearchOrganization> query = (TypedQuery<SearchOrganization>) em
				.createNativeQuery(
						"select o.OrganizationId,o.organizationName, o.Address1 AS Address1, o.Address2 AS Address2, c.CityName AS CityName,s.StateName AS StateName,cy.CountryName AS CountryName,o.ZipCode AS ZipCode from organization o join city c on ((o.CityID = c.CityId)) join state s on ((o.StateId = s.StateId)) join country cy on ((o.CountryID = cy.CountryID)) "
						+ "WHERE (:organizationName ='All%' or o.organizationName like :organizationName) AND (:zipCode='All%' or o.ZipCode like :zipCode) AND (:city='All%' or c.CityName like :city)",
						SearchOrganization.class).setParameter("organizationName", organizationName + "%")
						.setParameter("zipCode", zipCode + "%").setParameter("city", city + "%");
		  
	          System.out.println(query.toString());
	       	  
			  List<SearchOrganization> results = (List<SearchOrganization>)query.getResultList();
			  return results;
			  	
	}

}

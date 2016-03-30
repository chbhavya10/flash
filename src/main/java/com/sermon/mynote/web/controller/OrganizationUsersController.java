package com.sermon.mynote.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sermon.mynote.domain.OrganizationUsers;
import com.sermon.mynote.service.OrganizationUsersService;


@RequestMapping("/orglist")
@Controller
public class OrganizationUsersController {

final Logger logger = LoggerFactory.getLogger(OrganizationUsers.class);	
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	private OrganizationUsersService organizationUsersService;
	
	
	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<OrganizationUsers> getOrgUsrById(@PathVariable int id) {	 
		logger.info("Listing user favorite");	
		
		List<OrganizationUsers> userFavorites = organizationUsersService.findOrgUserById(id);
		return userFavorites;
	}
	

	
	@RequestMapping(value = "/add", method = RequestMethod.POST,headers = {"Content-type=application/json"})
	public @ResponseBody OrganizationUsers CreateOrgUser(@RequestBody OrganizationUsers organizationUsers) {
				
		
		OrganizationUsers newOrgUser=organizationUsersService.save(organizationUsers);
		return newOrgUser;
	}
	
	/*update  */
	@RequestMapping(value="/update/{id}", method=RequestMethod.PUT)
    @ResponseBody
    public void updateOrgUser(@RequestBody OrganizationUsers organizationUsers,@PathVariable Long id) {
        logger.info("Updating organization Users: " + organizationUsers);
        organizationUsersService.save(organizationUsers);
        logger.info("organizationUsers updated successfully with info: " + organizationUsers);
    }

	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable Long id) {
        logger.info("Deleting contact with id: " + id);
        OrganizationUsers orgUser = organizationUsersService.findById(id.intValue());
        organizationUsersService.delete(orgUser);
        logger.info("org User deleted successfully");
}
}

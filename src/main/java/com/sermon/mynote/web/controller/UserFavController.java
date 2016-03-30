
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

import com.sermon.mynote.domain.UserFavorite;
import com.sermon.mynote.domain.VwNotedetails;
import com.sermon.mynote.service.UserFavoriteService;



@Controller
public class UserFavController {

	final Logger logger = LoggerFactory.getLogger(UserFavController.class);	
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	private UserFavoriteService userFavoriteService;
	
	
	@RequestMapping(value = "/userfav/{id}", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<UserFavorite> getUsrFavById(@PathVariable int id) {	 
		logger.info("Listing user favorite");	
		
		List<UserFavorite> userFavorites = userFavoriteService.findUserFavByUserId(id);
		return userFavorites;
	}
	

	
	@RequestMapping(value = "/PostFavoritesData", method = RequestMethod.POST,headers = {"Content-type=application/json"})
	public @ResponseBody UserFavorite PostFavService(@RequestBody UserFavorite userFavorite) {
		
		System.out.println("userFavorite:--"+userFavorite.getUserId());
		
		
		UserFavorite newUserFavorite=userFavoriteService.save(userFavorite);
		
		return newUserFavorite;
	}
	
	/*update  */
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
    @ResponseBody
    public void updateUserFavorite(@RequestBody UserFavorite userFavorite,@PathVariable Long id) {
        logger.info("Updating user favorite: " + userFavorite);
        userFavoriteService.save(userFavorite);
        logger.info("Contact updated successfully with info: " + userFavorite);
    }

	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable Long id) {
        logger.info("Deleting contact with id: " + id);
        UserFavorite userFavorite = userFavoriteService.findById(id.intValue());
        userFavoriteService.delete(userFavorite);
        logger.info("UserFavoriteService deleted successfully");
}
}

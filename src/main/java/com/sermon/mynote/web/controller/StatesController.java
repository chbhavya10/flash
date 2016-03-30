package com.sermon.mynote.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sermon.mynote.domain.City;
import com.sermon.mynote.domain.Country;
import com.sermon.mynote.domain.State;
import com.sermon.mynote.service.StatesService;

@RequestMapping("/countries")
@Controller
public class StatesController {

	final Logger logger = LoggerFactory.getLogger(StatesController.class);

	@Autowired
	MessageSource messageSource;

	@Autowired
	private StatesService statesService;

	@RequestMapping(value = "/getStates", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public List<State> getStates(@RequestBody Country country) {
		logger.info("Listing States");

		List<State> states = statesService.findStateByCountryId(country.getCountryId());
		return states;
	}

	@RequestMapping(value = "/getCountries", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Country> getCountries() {
		logger.info("Listing Countries");

		List<Country> countries = statesService.findAll();
		return countries;
	}

	@RequestMapping(value = "/getCities", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public List<City> getCities(@RequestBody State state) {
		logger.info("Listing Cities");

		List<City> Cities = statesService.findCityByStateId(state.getStateId());
		return Cities;
	}

}

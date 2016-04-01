package com.sermon.mynote.web.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
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

import com.sermon.mynote.domain.AddSubSection;
import com.sermon.mynote.domain.StatusResponse;
import com.sermon.mynote.domain.SubSection;
import com.sermon.mynote.service.SubSectionService;

@RequestMapping("/subsection")
@Controller
public class SubSectionController {

	final Logger logger = LoggerFactory.getLogger(SubSectionController.class);

	@Autowired
	MessageSource messageSource;

	@Autowired
	private SubSectionService subsectionService;

	@RequestMapping(value = "/addSubSection", method = RequestMethod.POST, headers = {
			"Content-type=application/json" })
	public @ResponseBody SubSection PostSubsection(@RequestBody SubSection subsection) {

		SubSection newSubSection = subsectionService.save(subsection);

		return newSubSection;
	}

	/* update */
	@RequestMapping(value = "/updateSubSection/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public StatusResponse updateNote(@RequestBody AddSubSection subsection, @PathVariable Long id) {

		SubSection subsectionTemp = new SubSection();
		subsectionTemp = subsectionService.findById(id.intValue());
		subsectionTemp.setSectionId(subsection.getSectionId());
		List<Integer> subSectionKeywords = subsection.getSubsectionKeyWords();
		String subSectionKeyword = StringUtils.join(subSectionKeywords, ",");
		subsectionTemp.setSubsectionKeyWords(subSectionKeyword);
		subsectionTemp.setSubsectionText(subsection.getSubsectionText());

		SubSection subsectionResponse = subsectionService.save(subsectionTemp);

		StatusResponse response = new StatusResponse();

		if (subsectionResponse != null)
			response.setStatus(true);
		else
			response.setStatus(false);

		return response;

	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public StatusResponse delete(@PathVariable Long id) {
		logger.info("Deleting note with id: " + id);
		SubSection subsectionTemp = subsectionService.findById(id.intValue());
		subsectionService.delete(subsectionTemp);
		logger.info("subsection deleted successfully");
		StatusResponse status = new StatusResponse();
		status.setStatus(true);

		return status;
	}

	@RequestMapping(value = "/searchSubSection/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public SubSection getSubSectionById(@PathVariable int id) {
		logger.info("Listing subsection");

		SubSection subsection = subsectionService.findById(id);
		return subsection;
	}

}

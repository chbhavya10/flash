package com.sermon.mynote.service;

import com.sermon.mynote.domain.OrganizationFeedback;

public interface OrganizationFeedbackService {

	int updateOrgFeedback(int organizationId, int userId, double rating, String feedbackComments);

	OrganizationFeedback getFeedback(int organizationId, int userId);

}

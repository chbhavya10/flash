package com.sermon.mynote.service;

import java.util.List;

import com.sermon.mynote.domain.VwUserRequests;

public interface VwUserRequestsService {

	List<VwUserRequests> findRequestsByUserId(int id);

	List<VwUserRequests> findRequestsByOrgId(int orgId);

}

package com.sermon.mynote.service;

import java.util.List;

import com.sermon.mynote.domain.Request;
import com.sermon.mynote.domain.RequestDetails;
import com.sermon.mynote.domain.RequestType;

public interface RequestService {

	List<RequestType> findAll();

	Request save(Request request);

	RequestDetails findById(int id);

	int updateRequest(int requestId, String requestUpdate, String requestStatus);

}

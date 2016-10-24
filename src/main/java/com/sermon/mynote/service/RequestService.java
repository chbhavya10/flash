package com.sermon.mynote.service;

import java.util.List;

import com.sermon.mynote.domain.RequestType;

public interface RequestService {

	List<RequestType> findAll();

}

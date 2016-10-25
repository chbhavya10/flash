package com.sermon.mynote.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sermon.mynote.domain.VwUserRequests;

public interface VwUserRequestsRepository extends PagingAndSortingRepository<VwUserRequests, Long> {

	List<VwUserRequests> findRequestsByUserId(int userId);

	List<VwUserRequests> findRequestsByOrgId(int orgId);

}

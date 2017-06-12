package com.sermon.mynote.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sermon.mynote.domain.VwOrganizationInfo;

public interface VwOrganizationInfoRepository extends PagingAndSortingRepository<VwOrganizationInfo, Long> {

	List<VwOrganizationInfo> findOrganizationInfoByOrgId(int orgId);

}

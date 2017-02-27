package com.sermon.mynote.domain;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

public class SearchOrganizationResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigInteger totalCount;
	private List<OrganizationLike> result;

	public BigInteger getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(BigInteger totalCount) {
		this.totalCount = totalCount;
	}

	public List<OrganizationLike> getResult() {
		return result;
	}

	public void setResult(List<OrganizationLike> result) {
		this.result = result;
	}

}

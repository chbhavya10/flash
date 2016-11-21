package com.sermon.mynote.domain;

import java.io.Serializable;

public class OrganizationId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int organizationId;
	private String organizationName;

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public int getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}

}

package com.sermon.mynote.domain;

import java.io.Serializable;
import java.util.List;

public class RegisterUserResponse implements Serializable {

	private List<Organization> organizations;
	private boolean status;

	public List<Organization> getOrganizations() {
		return organizations;
	}

	public void setOrganizations(List<Organization> organizations) {
		this.organizations = organizations;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

}

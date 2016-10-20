package com.sermon.mynote.domain;

import java.io.Serializable;

public class ValidateOrgKeyResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean keyResponse;
	private boolean newAuthorAvailability;

	public boolean isKeyResponse() {
		return keyResponse;
	}

	public void setKeyResponse(boolean keyResponse) {
		this.keyResponse = keyResponse;
	}

	public boolean isNewAuthorAvailability() {
		return newAuthorAvailability;
	}

	public void setNewAuthorAvailability(boolean newAuthorAvailability) {
		this.newAuthorAvailability = newAuthorAvailability;
	}

}

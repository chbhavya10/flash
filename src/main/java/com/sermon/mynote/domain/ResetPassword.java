package com.sermon.mynote.domain;

import java.io.Serializable;

public class ResetPassword implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String verificationToken;
	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private int userId;

	public String getVerificationToken() {
		return verificationToken;
	}

	public void setVerificationToken(String verificationToken) {
		this.verificationToken = verificationToken;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}

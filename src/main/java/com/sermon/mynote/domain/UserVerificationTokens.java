package com.sermon.mynote.domain;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@NamedStoredProcedureQueries({

		@NamedStoredProcedureQuery(name = "UserVerificationTokens.update_tokenstatus", procedureName = "update_tokenstatus", parameters = {
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "userid", type = Integer.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "verificationtoken", type = String.class)

		}),
		@NamedStoredProcedureQuery(name = "UserVerificationTokens.delete_token", procedureName = "delete_token", parameters = {
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "userid", type = Integer.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "verificationtoken", type = String.class)

		}) })
@Table(name = "UserVerificationTokens")
public class UserVerificationTokens {

	private int userVerificationTokenId;
	private String verificationToken;
	private String password;
	private int isVerified;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	public int getUserVerificationTokenId() {
		return userVerificationTokenId;
	}

	public void setUserVerificationTokenId(int userVerificationTokenId) {
		this.userVerificationTokenId = userVerificationTokenId;
	}

	public int getIsVerified() {
		return isVerified;
	}

	public void setIsVerified(int isVerified) {
		this.isVerified = isVerified;
	}

	public String getVerificationToken() {
		return verificationToken;
	}

	public void setVerificationToken(String verificationToken) {
		this.verificationToken = verificationToken;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

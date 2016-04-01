package com.sermon.mynote.domain;

// Generated May 7, 2015 9:40:59 PM by Hibernate Tools 4.0.0

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

/**
 * User generated by hbm2java
 */

/*
 * @NamedNativeQueries({
 * 
 * @NamedNativeQuery( name = "add_user", query =
 * "call add_user (:username, :useremail, :userpassword);", resultClass =
 * User.class ) })
 */

@Entity
@NamedStoredProcedureQueries({

		@NamedStoredProcedureQuery(name = "User.add_user", procedureName = "add_user", parameters = {
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "username", type = String.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "useremail", type = String.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "userpassword", type = String.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "createDt", type = Timestamp.class)

		}),

		@NamedStoredProcedureQuery(name = "User.update_userpassword", procedureName = "update_userpassword", parameters = {
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "userId", type = Integer.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "userPwd", type = String.class)

		}),

		@NamedStoredProcedureQuery(name = "User.update_user_role", procedureName = "update_user_role", parameters = {
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "roleId", type = Integer.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "userId", type = Integer.class) })

})
@Table(name = "user")
public class User implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer userId;
	private String userName;
	private String userEmail;
	private String userPassword;
	private String userStatus;
	private DateTime createDt;
	private DateTime updateDt;
	private String userMobile;

	@Column(name = "UserPhone")
	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public User() {
	}

	public User(String userName, String userEmail, String userPassword,
			String userStatus) {
		this.userName = userName;
		this.userEmail = userEmail;
		this.userPassword = userPassword;
		this.userStatus = userStatus;
	}

	public User(String userName, String userEmail, String userPassword,
			String userStatus, DateTime createDt, DateTime updateDt,
			String userMobile) {
		this.userName = userName;
		this.userEmail = userEmail;
		this.userPassword = userPassword;
		this.userStatus = userStatus;
		this.createDt = createDt;
		this.updateDt = updateDt;
		this.userMobile = userMobile;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "UserID")
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return this.userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserStatus() {
		return this.userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	@Column(name = "Create_Dt")
	// @Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	// @DateTimeFormat(iso=ISO.DATE)
	public DateTime getCreateDt() {

		return this.createDt;
	}

	public void setCreateDt(DateTime createDt) {
		this.createDt = createDt;
	}

	@Column(name = "Update_Dt")
	// @Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	// @DateTimeFormat(iso=ISO.DATE)
	public DateTime getUpdateDt() {
		return this.updateDt;
	}

	public void setUpdateDt(DateTime updateDt) {
		this.updateDt = updateDt;
	}

}

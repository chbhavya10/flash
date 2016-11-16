package com.sermon.mynote.domain;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
@Table(name = "OrganizationAuthors")
public class OrganizationAuthors {

	private int organizationAuthorId;
	private int organizationId;
	private int userId;
	private DateTime createDate;
	private DateTime modifyDate;
	private String status;

	public OrganizationAuthors() {
	}

	public OrganizationAuthors(int organizationAuthorId, int organizationId, int userId, DateTime createDate,
			DateTime modifyDate, String status) {
		super();
		this.organizationAuthorId = organizationAuthorId;
		this.organizationId = organizationId;
		this.userId = userId;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
		this.status = status;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "OrganizationAuthorID")
	public int getOrganizationAuthorId() {
		return organizationAuthorId;
	}

	public void setOrganizationAuthorId(int organizationAuthorId) {
		this.organizationAuthorId = organizationAuthorId;
	}

	public int getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	public DateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(DateTime createDate) {
		this.createDate = createDate;
	}

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	public DateTime getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(DateTime modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}

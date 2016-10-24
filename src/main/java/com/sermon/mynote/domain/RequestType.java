package com.sermon.mynote.domain;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RequestType")
public class RequestType {

	private int requestTypeId;
	private String requestType;
	private String requestGroup;
	private String requestText;

	public RequestType() {

	}

	public RequestType(String requestType, String requestGroup, String requestText) {

		this.requestType = requestType;
		this.requestGroup = requestGroup;
		this.requestText = requestText;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "RequestTypeID")
	public int getRequestTypeId() {
		return requestTypeId;
	}

	public void setRequestTypeId(int requestTypeId) {
		this.requestTypeId = requestTypeId;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getRequestGroup() {
		return requestGroup;
	}

	public void setRequestGroup(String requestGroup) {
		this.requestGroup = requestGroup;
	}

	public String getRequestText() {
		return requestText;
	}

	public void setRequestText(String requestText) {
		this.requestText = requestText;
	}

}

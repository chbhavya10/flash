package com.sermon.mynote.domain;

import java.io.Serializable;

public class EventsList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	int eventId;
	String eventType;
	String organization;
	String eventLocation;
	String FromDate;
	String ToDate;

	public EventsList(int eventId, String eventType, String organization, String eventLocation, String fromDate,
			String toDate) {
		super();
		this.eventId = eventId;
		this.eventType = eventType;
		this.organization = organization;
		this.eventLocation = eventLocation;
		FromDate = fromDate;
		ToDate = toDate;
	}

	/**
	 * @return the fromDate
	 */
	public String getFromDate() {
		return FromDate;
	}

	/**
	 * @param fromDate
	 *            the fromDate to set
	 */
	public void setFromDate(String fromDate) {
		FromDate = fromDate;
	}

	/**
	 * @return the toDate
	 */
	public String getToDate() {
		return ToDate;
	}

	/**
	 * @param toDate
	 *            the toDate to set
	 */
	public void setToDate(String toDate) {
		ToDate = toDate;
	}

	/**
	 * @return the eventId
	 */
	public int getEventId() {
		return eventId;
	}

	/**
	 * @param eventId
	 *            the eventId to set
	 */
	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	/**
	 * @return the eventType
	 */
	public String getEventType() {
		return eventType;
	}

	/**
	 * @param eventType
	 *            the eventType to set
	 */
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	/**
	 * @return the organization
	 */
	public String getOrganization() {
		return organization;
	}

	/**
	 * @param organization
	 *            the organization to set
	 */
	public void setOrganization(String organization) {
		this.organization = organization;
	}

	/**
	 * @return the eventLocation
	 */
	public String getEventLocation() {
		return eventLocation;
	}

	/**
	 * @param eventLocation
	 *            the eventLocation to set
	 */
	public void setEventLocation(String eventLocation) {
		this.eventLocation = eventLocation;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "EventsList [eventId=" + eventId + ", eventType=" + eventType + ", organization=" + organization
				+ ", eventLocation=" + eventLocation + ", FromDate=" + FromDate + ", ToDate=" + ToDate + "]";
	}

}

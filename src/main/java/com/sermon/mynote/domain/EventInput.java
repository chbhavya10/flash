package com.sermon.mynote.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.joda.time.DateTime;


@Entity
@Table(name = "Event")
public class EventInput implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String EventLocation;
	int OrganizationID;
	String Address1;
	String Address2;
	int CityId;
	int Stateid;
	int CountryID;
	String ZipCode;
	double LocationLat;
	double LocationLong;
	String OtherInfo;
	DateTime datetime;
	int EventId;
	int EventTypeId;
	DateTime FromDate;
	DateTime ToDate;
	String Title;
	String Description;
	String Host;
	String ContactEmail;
	String ContactPhone;
	String ContactWebSite;
	String ContactFB;
	DateTime ModifiedDate;
	int FavoriteEventId;



	/**
	 * @return the eventLocation
	 */
	public String getEventLocation() {
		return EventLocation;
	}

	/**
	 * @param eventLocation
	 *            the eventLocation to set
	 */
	public void setEventLocation(String eventLocation) {
		EventLocation = eventLocation;
	}

	/**
	 * @return the organizationID
	 */
	public int getOrganizationID() {
		return OrganizationID;
	}

	/**
	 * @param organizationID
	 *            the organizationID to set
	 */
	public void setOrganizationID(int organizationID) {
		OrganizationID = organizationID;
	}

	/**
	 * @return the address1
	 */
	public String getAddress1() {
		return Address1;
	}

	/**
	 * @param address1
	 *            the address1 to set
	 */
	public void setAddress1(String address1) {
		Address1 = address1;
	}

	/**
	 * @return the address2
	 */
	public String getAddress2() {
		return Address2;
	}

	/**
	 * @param address2
	 *            the address2 to set
	 */
	public void setAddress2(String address2) {
		Address2 = address2;
	}

	/**
	 * @return the cityId
	 */
	public int getCityId() {
		return CityId;
	}

	/**
	 * @param cityId
	 *            the cityId to set
	 */
	public void setCityId(int cityId) {
		CityId = cityId;
	}

	/**
	 * @return the stateid
	 */
	public int getStateid() {
		return Stateid;
	}

	/**
	 * @param stateid
	 *            the stateid to set
	 */
	public void setStateid(int stateid) {
		Stateid = stateid;
	}

	/**
	 * @return the countryID
	 */
	public int getCountryID() {
		return CountryID;
	}

	/**
	 * @param countryID
	 *            the countryID to set
	 */
	public void setCountryID(int countryID) {
		CountryID = countryID;
	}

	/**
	 * @return the zipCode
	 */
	public String getZipCode() {
		return ZipCode;
	}

	/**
	 * @param zipCode
	 *            the zipCode to set
	 */
	public void setZipCode(String zipCode) {
		ZipCode = zipCode;
	}

	/**
	 * @return the locationLat
	 */
	public double getLocationLat() {
		return LocationLat;
	}

	/**
	 * @param locationLat
	 *            the locationLat to set
	 */
	public void setLocationLat(double locationLat) {
		LocationLat = locationLat;
	}

	/**
	 * @return the locationLong
	 */
	public double getLocationLong() {
		return LocationLong;
	}

	/**
	 * @param locationLong
	 *            the locationLong to set
	 */
	public void setLocationLong(double locationLong) {
		LocationLong = locationLong;
	}

	/**
	 * @return the otherInfo
	 */
	public String getOtherInfo() {
		return OtherInfo;
	}

	/**
	 * @param otherInfo
	 *            the otherInfo to set
	 */
	public void setOtherInfo(String otherInfo) {
		OtherInfo = otherInfo;
	}

	/**
	 * @return the datetime
	 */
	public DateTime getDatetime() {
		return datetime;
	}

	/**
	 * @param datetime
	 *            the datetime to set
	 */
	public void setDatetime(DateTime datetime) {
		this.datetime = datetime;
	}

	/**
	 * @return the eventId
	 */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "EventId")
	public int getEventId() {
		return EventId;
	}

	/**
	 * @param eventId
	 *            the eventId to set
	 */
	public void setEventId(int eventId) {
		EventId = eventId;
	}

	/**
	 * @return the eventTypeId
	 */
	public int getEventTypeId() {
		return EventTypeId;
	}

	/**
	 * @param eventTypeId
	 *            the eventTypeId to set
	 */
	public void setEventTypeId(int eventTypeId) {
		EventTypeId = eventTypeId;
	}

	/**
	 * @return the fromDate
	 */
	public DateTime getFromDate() {
		return FromDate;
	}

	/**
	 * @param fromDate
	 *            the fromDate to set
	 */
	public void setFromDate(DateTime fromDate) {
		FromDate = fromDate;
	}

	/**
	 * @return the toDate
	 */
	public DateTime getToDate() {
		return ToDate;
	}

	/**
	 * @param toDate
	 *            the toDate to set
	 */
	public void setToDate(DateTime toDate) {
		ToDate = toDate;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return Title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		Title = title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return Description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		Description = description;
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return Host;
	}

	/**
	 * @param host
	 *            the host to set
	 */
	public void setHost(String host) {
		Host = host;
	}

	/**
	 * @return the contactEmail
	 */
	public String getContactEmail() {
		return ContactEmail;
	}

	/**
	 * @param contactEmail
	 *            the contactEmail to set
	 */
	public void setContactEmail(String contactEmail) {
		ContactEmail = contactEmail;
	}

	/**
	 * @return the contactPhone
	 */
	public String getContactPhone() {
		return ContactPhone;
	}

	/**
	 * @param contactPhone
	 *            the contactPhone to set
	 */
	public void setContactPhone(String contactPhone) {
		ContactPhone = contactPhone;
	}

	/**
	 * @return the contactWebSite
	 */
	public String getContactWebSite() {
		return ContactWebSite;
	}

	/**
	 * @param contactWebSite
	 *            the contactWebSite to set
	 */
	public void setContactWebSite(String contactWebSite) {
		ContactWebSite = contactWebSite;
	}

	/**
	 * @return the contactFB
	 */
	public String getContactFB() {
		return ContactFB;
	}

	/**
	 * @param contactFB
	 *            the contactFB to set
	 */
	public void setContactFB(String contactFB) {
		ContactFB = contactFB;
	}

	/**
	 * @return the modifiedDate
	 */
	public DateTime getModifiedDate() {
		return ModifiedDate;
	}

	/**
	 * @param modifiedDate
	 *            the modifiedDate to set
	 */
	public void setModifiedDate(DateTime modifiedDate) {
		ModifiedDate = modifiedDate;
	}

	public int getFavoriteEventId() {
		return FavoriteEventId;
	}

	public void setFavoriteEventId(int favoriteEventId) {
		FavoriteEventId = favoriteEventId;
	}
 

	
	
}

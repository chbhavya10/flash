package com.sermon.mynote.domain;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
//@NamedQuery(name = "VwUserprofile.findUserProfileByUserId", query = "SELECT o FROM VwUserprofile o  WHERE o.UserId = (?1)")
@Table(name = "vw_userprofile")
public class VwUserprofile implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1539928465361825718L;
	/**
	 * 
	 */

	private int UserProfileID;
	private int UserId;
	private String FirstName;
	private String LastName;
	private String NickName;
	private String Address_1;
	private String Address_2;
	private String CityName;
	private String StateName;
	private String CountryName;
	private String ZipCode;
	private Date DOB;
	private String Gender;
	
	public  VwUserprofile(){
		
	}
	
	
	public  VwUserprofile(int UserId,String FirstName,String LastName,String NickName,
    		String Address_1,String Address_2,String CityName,String StateName){
    	    
    	// this.UserProfileID=UserProfileID;
    	 this.UserId=UserId;
    	 this.FirstName=FirstName;
    	 this.LastName=LastName;
    	 this.NickName=NickName;
    	 this.Address_1=Address_1;
    	 this.Address_2=Address_2;
    	 this.CityName=CityName;
    	 this.StateName=StateName;
    	 
    			 
		
	}
	
	
    public  VwUserprofile(int UserId,String FirstName,String LastName,String NickName,
    		String Address_1,String Address_2,String CityName,String StateName,String CountryName,String ZipCode,
    		Date DOB,String Gender){
    	    
    	// this.UserProfileID=UserProfileID;
    	 this.UserId=UserId;
    	 this.FirstName=FirstName;
    	 this.LastName=LastName;
    	 this.NickName=NickName;
    	 this.Address_1=Address_1;
    	 this.Address_2=Address_2;
    	 this.CityName=CityName;
    	 this.StateName=StateName;
    	 this.CountryName=CountryName;
    	 this.ZipCode=ZipCode;
    	 this.DOB=DOB;
    	 this.Gender=Gender;
    			 
		
	}
   
    @Id
	@Column(name = "UserProfileID")
	public int getUserProfileID() {
		return UserProfileID;
	}

	public void setUserProfileID(int userProfileID) {
		this.UserProfileID = userProfileID;
	}

	public int getUserId() {
		return this.UserId;
	}

	public void setUserId(int userId) {
		this.UserId = userId;
	}

	public String getFirstName() {
		return this.FirstName;
	}

	public void setFirstName(String firstName) {
		this.FirstName = firstName;
	}

	public String getLastName() {
		return this.LastName;
	}

	public void setLastName(String lastName) {
		this.LastName = lastName;
	}

	public String getNickName() {
		return this.NickName;
	}

	public void setNickName(String nickName) {
		this.NickName = nickName;
	}

	public String getAddress_1() {
		return this.Address_1;
	}

	public void setAddress_1(String address_1) {
		this.Address_1 = address_1;
	}

	public String getAddress_2() {
		return this.Address_2;
	}

	public void setAddress_2(String address_2) {
		this.Address_2 = address_2;
	}

	public String getCityName() {
		return this.CityName;
	}

	public void setCityName(String cityName) {
		this.CityName = cityName;
	}

	public String getStateName() {
		return this.StateName;
	}

	public void setStateName(String stateName) {
		this.StateName = stateName;
	}

	public String getCountryName() {
		return this.CountryName;
	}

	public void setCountryName(String countryName) {
		this.CountryName = countryName;
	}

	public String getZipCode() {
		return this.ZipCode;
	}

	public void setZipCode(String zipCode) {
		this.ZipCode = zipCode;
	}

	public Date getDOB() {
		return this.DOB;
	}

	public void setDOB(Date dOB) {
		this.DOB = dOB;
	}

	public String getGender() {
		return this.Gender;
	}

	public void setGender(String gender) {
		this.Gender = gender;
	}

	
    
    
	
}

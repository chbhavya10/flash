package com.sermon.mynote.payments.stripe;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public class ChargeRequest {

	public enum Currency {
	        EUR, USD;
		
		 public static <T extends Enum<T>> T getEnumFromString(Class<T> enumClass, String value) {
		        if (enumClass == null) {
		            throw new IllegalArgumentException("EnumClass value can't be null.");
		        }

		        for (Enum<?> enumValue : enumClass.getEnumConstants()) {
		            if (enumValue.toString().equalsIgnoreCase(value)) {
		                return (T) enumValue;
		            }
		        }

		        //Construct an error message that indicates all possible values for the enum.
		        StringBuilder errorMessage = new StringBuilder();
		        boolean bFirstTime = true;
		        for (Enum<?> enumValue : enumClass.getEnumConstants()) {
		            errorMessage.append(bFirstTime ? "" : ", ").append(enumValue);
		            bFirstTime = false;
		        }
		        throw new IllegalArgumentException(value + " is invalid value. Supported values are " + errorMessage);
		}
		@JsonCreator
        public static Currency fromValue(String value) {
            return getEnumFromString(Currency.class, value);
        }

        @JsonValue
        public String toJson() {
            return name().toLowerCase();
        } 
    }
	
	private String stripeEmail;
	   
    private String description;
    private Integer amount;
    private Currency currency;
    private Integer orgId;
    private Integer userId;
    private String type;
    private String notes;
    private String destinationAcctId;
    private String source;
    
	public String getStripeEmail() {
		return stripeEmail;
	}
	public void setStripeEmail(String stripeEmail) {
		this.stripeEmail = stripeEmail;
	}
    
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Currency getCurrency() {
		return currency;
	}
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	
	public Integer getOrgId() {
		return orgId;
	}
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	 
	public String getDestinationAcctId() {
		return destinationAcctId;
	}
	public void setDestinationAcctId(String destinationAcctId) {
		this.destinationAcctId = destinationAcctId;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
    
    
    
}

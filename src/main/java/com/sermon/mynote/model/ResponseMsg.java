package com.sermon.mynote.model;

import java.io.Serializable;

public class ResponseMsg implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String message;
	 
	public ResponseMsg(String msg){
		this.message = msg;
	}
	
	public String getMessage() {
		return message;
	}
 
	public void setMessage(String message) {
		this.message = message;
	}
}

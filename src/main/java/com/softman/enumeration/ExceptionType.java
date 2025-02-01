package com.softman.enumeration;

public enum ExceptionType {

	SERVER_EXCEPTION("Server Exception"),
	BUSINESS_EXCEPTION("Business Exception");
	
	private String value;
	
	ExceptionType(String value){
		this.value = value;
	}
	
	public String getValue(){
		return value;
	}
}

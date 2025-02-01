package com.softman.enumeration;

public enum RolEnum {

	ADMIN("ADMIN"),
	MEDICO("MEDICO"),
	PACIENTE("PACIENTE");
	
	private String value;
	
	RolEnum(String value){
		this.value = value;
	}	
	
	public String getValue(){
		return value;
	}
	
}

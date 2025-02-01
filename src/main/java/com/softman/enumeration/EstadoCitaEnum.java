package com.softman.enumeration;

public enum EstadoCitaEnum {

	SIN_ASIGNAR("Sin asignar"),
	ASIGNADA("Asignada"),
	REASIGNADA("Reasignada"),
	ATENDIDA("Atendida"),
	CANCELADA("Cancelada"),
	PACIENTE_NO_ASISTIO("Paciente no asistió");
	
	private String value;
	
	EstadoCitaEnum(String value){
		this.value = value;
	}	
	
	public String getValue(){
		return value;
	}
}

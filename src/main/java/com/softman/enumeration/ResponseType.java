package com.softman.enumeration;

public enum ResponseType {

	RECURSO_A_ACTUALIZAR_NO_ENCONTRADO("Recurso a actualizar no encontrado"),
	RECURSO_A_ELIMINAR_NO_ENCONTRADO("Recurso a eliminar no encontrado"),
	RECURSO_ELIMINADO("Recurso eliminado");
	
	private String value;
	
	ResponseType(String value){
		this.value = value;
	}
	
	public String getValue(){
		return value;
	}
}

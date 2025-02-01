package com.softman.dto;

import java.sql.Timestamp;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class RolDTO {

	@JsonProperty("id")
	private Long id;
	
	@JsonProperty("nombre")
	private String nombre;
	
	@JsonProperty("descripcion")
	private String descripcion;
	
	@JsonProperty("createdAt")
	private Timestamp createdAt;
	
	@JsonProperty("modifiedAt")
	private Timestamp modifiedAt;
	
	@JsonProperty("permisos")
	private Set<PermisoDTO> permisos;
	
}

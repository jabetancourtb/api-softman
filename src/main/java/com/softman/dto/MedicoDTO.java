package com.softman.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class MedicoDTO {

	@JsonProperty("id")
	private Long id;
	
	@Column(name = "rethus", nullable = false)
	private String rethus;
	
	@Column(name = "documento", nullable = false)
	private String documento;
	
	@Column(name = "primerNombre", nullable = false)
	private String primerNombre;
		
	@Column(name = "segundoNombre", nullable = false)
	private String segundoNombre;
	
	@Column(name = "primerApellido", nullable = false)
	private String primerApellido;
	
	@Column(name = "segundoApellido", nullable = false)
	private String segundoApellido;
	
	@JsonProperty("createdAt")
	private Timestamp createdAt;
	
	@JsonProperty("modifiedAt")
	private Timestamp modifiedAt;
	
	@NotNull(message = "El parámetro idUsuario no debe ser nulo ni vacío")
	@JsonProperty("idUsuario")
	private Long idUsuario;
	
}

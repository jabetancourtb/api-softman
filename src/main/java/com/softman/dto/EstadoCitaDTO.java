package com.softman.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class EstadoCitaDTO {

	@JsonProperty("id")
	private Long id;
	
	@NotBlank(message = "El parámetro estado no debe ser nulo ni vacío")
	@JsonProperty("estado")
	private String estado;
	
	@NotBlank(message = "El parámetro descripcion no debe ser nulo ni vacío")
	@JsonProperty("descripcion")
	private String descripcion;
		
	@JsonProperty("createdAt")
	private Timestamp createdAt;
	
	@JsonProperty("modifiedAt")
	private Timestamp modifiedAt;
	
}

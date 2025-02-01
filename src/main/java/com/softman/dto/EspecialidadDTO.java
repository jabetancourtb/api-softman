package com.softman.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class EspecialidadDTO {

	@JsonProperty("id")
	private Long id;
	
	@NotBlank(message = "El parámetro nombre no debe ser nulo ni vacío")
	@Column(name = "nombre", nullable = false)
	private String nombre;
	
	@NotBlank(message = "El parámetro descripcion no debe ser nulo ni vacío")
	@Column(name = "descripcion", nullable = false)
	private String descripcion;
		
	@JsonProperty("createdAt")
	private Timestamp createdAt;
	
	@JsonProperty("modifiedAt")
	private Timestamp modifiedAt;
	
}

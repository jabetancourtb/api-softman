package com.softman.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class DireccionDTO {

	@JsonProperty("id")
	private Long id;
	
	@NotBlank(message = "El parámetro direccion no debe ser nulo ni vacío")
	@Column(name = "direccion", nullable = false)
	private String direccion;
			
	@JsonProperty("createdAt")
	private Timestamp createdAt;
	
	@JsonProperty("modifiedAt")
	private Timestamp modifiedAt;
	
	@NotNull(message = "El parámetro idUsuario no debe ser nulo ni vacío")
	@JsonProperty("idUsuario")
	private Long idUsuario;
	
}

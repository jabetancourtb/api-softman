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
public class TelefonoDTO {

	@JsonProperty("id")
	private Long id;
	
	@NotBlank(message = "El parámetro telefono no debe ser nulo ni vacío")
	@Column(name = "telefono", nullable = false)
	private String telefono;
			
	@JsonProperty("createdAt")
	private Timestamp createdAt;
	
	@JsonProperty("modifiedAt")
	private Timestamp modifiedAt;
	
	@NotNull(message = "El parámetro idUsuario no debe ser nulo ni vacío")
	@JsonProperty("idUsuario")
	private Long idUsuario;
	
}

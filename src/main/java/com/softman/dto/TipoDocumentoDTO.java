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
public class TipoDocumentoDTO {

	
	@JsonProperty("id")
	private Long id;
	
	@NotBlank(message = "El parámetro codigoDocumento no debe ser nulo ni vacío")
	@Column(name = "codigoDocumento", nullable = false)
	private String codigoDocumento;
	
	@NotBlank(message = "El parámetro nombreDocumento no debe ser nulo ni vacío")
	@Column(name = "nombreDocumento", nullable = false)
	private String nombreDocumento;
			
	@JsonProperty("createdAt")
	private Timestamp createdAt;
	
	@JsonProperty("modifiedAt")
	private Timestamp modifiedAt;
	
	
}

package com.softman.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class CitaPartialUpdateDTO {

	@NotNull(message = "El parámetro idEstadoCita no puede ser nulo ni vacío")
	@JsonProperty("idEstadoCita")
	private Long idEstadoCita;
	
}

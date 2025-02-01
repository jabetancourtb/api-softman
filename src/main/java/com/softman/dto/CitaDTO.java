package com.softman.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class CitaDTO {

	@JsonProperty("id")
	private Long id;
	
	@JsonProperty("fechaHora")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
	private Timestamp fechaHora;
		
	@JsonProperty("createdAt")
	private Timestamp createdAt;
	
	@JsonProperty("modifiedAt")
	private Timestamp modifiedAt;
	
	@JsonProperty("estadoCita")
	private EstadoCitaDTO estadoCita;
	
	@JsonProperty("centroMedico")
	private CentroMedicoDTO centroMedico;
	
	@JsonProperty("especialidad")
	private EspecialidadDTO especialidad;
	
	@JsonProperty("medico")
	private MedicoDTO medico;
	
	@JsonProperty("paciente")
	private PacienteDTO paciente;
	
}

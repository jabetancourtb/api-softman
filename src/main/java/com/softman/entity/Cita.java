package com.softman.entity;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Entity
@Table(name = "citas")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Cita extends BaseEntityListener {
		
	private static final long serialVersionUID = 1L;

	@Column(name = "fecha_hora", nullable = false)
	private Timestamp fechaHora;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_estado_cita")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@JsonProperty(access = Access.WRITE_ONLY)
	private EstadoCita estadoCita;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_centro_medico")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@JsonProperty(access = Access.WRITE_ONLY)
	private CentroMedico centroMedico;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_especialidad")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@JsonProperty(access = Access.WRITE_ONLY)
	private Especialidad especialidad;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_medico")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@JsonProperty(access = Access.WRITE_ONLY)
	private Medico medico;
	
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_paciente", nullable = true)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@JsonProperty(access = Access.WRITE_ONLY)
	private Paciente paciente;
		
}

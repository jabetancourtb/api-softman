package com.softman.dto;

import java.sql.Timestamp;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UsuarioDTO {

	@JsonProperty("id")
	private Long id;
		
	@NotNull(message = "El parámetro documento no debe ser nulo ni vacío")
	@Column(name = "documento", nullable = false)
	private String documento;
	
	@Column(name = "nombreEmpresa", nullable = false)
	private String nombreEmpresa;
	
	@NotNull(message = "El parámetro primerNombre no debe ser nulo ni vacío")
	@Column(name = "primerNombre", nullable = false)
	private String primerNombre;
		
	@Column(name = "segundoNombre", nullable = false)
	private String segundoNombre;
	
	@NotNull(message = "El parámetro primerApellido no debe ser nulo ni vacío")
	@Column(name = "primerApellido", nullable = false)
	private String primerApellido;
	
	@Column(name = "segundoApellido", nullable = false)
	private String segundoApellido;
	
	@NotNull(message = "El parámetro correo no debe ser nulo ni vacío")
	@Column(name = "correo", nullable = false)
	private String correo;
	
	@JsonProperty("estaHabilitado")
	private boolean estaHabilitado;
	
	@JsonProperty("createdAt")
	private Timestamp createdAt;
	
	@JsonProperty("modifiedAt")
	private Timestamp modifiedAt;
	
	@JsonProperty("roles")
	private Set<RolDTO> roles;
	
	@JsonProperty("documentType")
	private TipoDocumentoDTO tipoDocumento;
	
	@JsonProperty("direcciones")
	private Set<DireccionDTO> direcciones;
	
	@JsonProperty("telefonos")
	private Set<TelefonoDTO> telefonos;

	
}

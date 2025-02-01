package com.softman.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistroResponseDTO {

	@JsonProperty("correo")
	private String correo;
	
	@JsonProperty("password")
	private String password;
	
	@JsonProperty("documento")
	private String documento;
	
	@JsonProperty("nombreEmpresa")
	private String nombreEmpresa;
	
	@JsonProperty("primerNombre")
	private String primerNombre;
	
	@JsonProperty("segundoNombre")
	private String segundoNombre;
	
	@JsonProperty("primerApellido")
	private String primerApellido;
	
	@JsonProperty("segundoApellido")
	private String segundoApellido;
	
	@JsonProperty("estaHabilitado")
	private boolean estaHabilitado;
	
	@JsonProperty("idRol")
	private Long idRol;
	
	@JsonProperty("idTipoDocumento")
	private Long idTipoDocumento;
	
	@JsonProperty("idUsuario")
	private Long idUsuario;
	
	@JsonProperty("idMedico")
	private Long idMedico;
	
	@JsonProperty("idPaciente")
	private Long idPaciente;
	
	@JsonProperty("direcciones")
	private List<DireccionDTO> direcciones;
	
	@JsonProperty("telefonos")
	private List<TelefonoDTO> telefonos;
	
	@JsonProperty("autenticacionResponseDTO")
	private AutenticacionResponseDTO autenticacionResponseDTO;	
	
}

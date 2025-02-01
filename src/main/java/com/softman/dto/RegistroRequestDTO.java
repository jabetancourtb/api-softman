package com.softman.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistroRequestDTO {

	@NotBlank(message = "El parámetro correo no puede ser nulo o vacío")
	@Email(message = "correo inválido")
	@JsonProperty("correo")
	private String correo;
	
	@NotBlank(message = "El parámetro password no puede ser nulo o vacío")
	@JsonProperty("password")
	private String password;
	
	@NotBlank(message = "El parámetro documento no puede ser nulo o vacío")
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
	
	@JsonProperty("rethus")
	private String rethus;
	
	@NotNull(message = "El parámetro idRol no puede ser nulo o vacío")
	@JsonProperty("idRol")
	private Long idRol;
	
	@NotNull(message = "El parámetro idTipoDocumento no puede ser nulo o vacío")
	@JsonProperty("idTipoDocumento")
	private Long idTipoDocumento;
	
	@JsonProperty("direcciones")
	private List<DireccionDTO> direcciones;
	
	@JsonProperty("telefonos")
	private List<TelefonoDTO> telefonos;
	
}

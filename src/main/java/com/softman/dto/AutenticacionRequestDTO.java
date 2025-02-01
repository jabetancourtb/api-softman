package com.softman.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AutenticacionRequestDTO {
	
	@NotBlank(message = "El parámetro correo no debe ser nulo ni vacío")
	private String correo;
	
	@NotBlank(message = "El parámetro password no debe ser nulo ni vacío")
	private String password;
	
}

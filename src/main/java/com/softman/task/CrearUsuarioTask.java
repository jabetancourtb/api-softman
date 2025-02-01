package com.softman.task;

import com.softman.dto.RegistroRequestDTO;
import com.softman.dto.RegistroResponseDTO;

public interface CrearUsuarioTask {

	RegistroResponseDTO crearUsuario(RegistroRequestDTO registroRequestDTO) throws Exception;
	
}

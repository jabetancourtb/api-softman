package com.softman.strategy.usuario;

import com.softman.dto.RegistroRequestDTO;
import com.softman.entity.Usuario;

public interface CrearSubUsuarioStrategy {

	void registrar();
	 
	Long crearSubUsuario(Usuario nuevoUsuario, RegistroRequestDTO registroRequestDTO) throws Exception;
}

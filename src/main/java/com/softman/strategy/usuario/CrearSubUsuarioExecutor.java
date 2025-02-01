package com.softman.strategy.usuario;

import com.softman.dto.RegistroRequestDTO;
import com.softman.entity.Usuario;
import com.softman.enumeration.RolEnum;

public interface CrearSubUsuarioExecutor {

	Long crearSubUsuario(RolEnum rol, Usuario nuevoUsuario, RegistroRequestDTO registroRequestDTO) throws Exception; 
}

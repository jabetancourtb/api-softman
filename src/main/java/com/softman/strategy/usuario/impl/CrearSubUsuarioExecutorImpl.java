package com.softman.strategy.usuario.impl;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.softman.dto.RegistroRequestDTO;
import com.softman.entity.Usuario;
import com.softman.enumeration.RolEnum;
import com.softman.strategy.usuario.CrearSubUsuarioExecutor;
import com.softman.strategy.usuario.CrearSubUsuarioStrategy;


@Service
public class CrearSubUsuarioExecutorImpl implements CrearSubUsuarioExecutor {
	
	private static HashMap<RolEnum, CrearSubUsuarioStrategy> createUserStrategyMap = new HashMap<>();
	
	  
    public static void agregarCrearSubUsuarioStrategy(RolEnum rol, CrearSubUsuarioStrategy crearSubUsuarioStrategy) {
    	createUserStrategyMap.put(rol, crearSubUsuarioStrategy);
    }

    
	@Override
	public Long crearSubUsuario(RolEnum rol, Usuario nuevoUsuario, RegistroRequestDTO registroRequestDTO) throws Exception {
		return createUserStrategyMap.get(rol).crearSubUsuario(nuevoUsuario, registroRequestDTO);
	}

}

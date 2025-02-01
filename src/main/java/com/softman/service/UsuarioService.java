package com.softman.service;

import org.springframework.data.domain.Page;

import com.softman.entity.Usuario;

public interface UsuarioService {

	Usuario guardarUsuario(Usuario usuario) throws Exception;
	
	void eliminarUsuarioPorId(Long idUsuario) throws Exception;
	
	Usuario buscarUsuarioPorId(Long idUsuario) throws Exception;
	
	Usuario buscarUsuarioPorCorreo(String correo) throws Exception;
	
	Page<Usuario> buscarUsuariosPorPaginacion(Integer pagina, Integer tamanioPagina, String campo, boolean asc) throws Exception;
	
}

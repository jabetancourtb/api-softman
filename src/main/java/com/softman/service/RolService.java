package com.softman.service;

import org.springframework.data.domain.Page;

import com.softman.entity.Rol;

public interface RolService {

	Rol guardarRol(Rol rol) throws Exception;
	
	void eliminarRolPorId(Long idRol) throws Exception;
	
	Rol buscarRolPorId(Long idRol) throws Exception;
	
	Rol buscarRolPorNombre(String nombre) throws Exception;
	
	Page<Rol> buscarRolesPorPaginacion(Integer pagina, Integer tamanioPagina, String campo, boolean asc) throws Exception;
	
	
	Page<Rol> buscarRolesPorPaginacionYPorIdUsuario(Long idUsuario, Integer pagina, Integer tamanioPagina, String campo, boolean asc) throws Exception;
	
}

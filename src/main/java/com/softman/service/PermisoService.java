package com.softman.service;

import org.springframework.data.domain.Page;

import com.softman.entity.Permiso;

public interface PermisoService {

	Permiso guardarPermiso(Permiso permiso) throws Exception;
	
	void eliminarPermisoPorId(Long idPermiso) throws Exception;
	
	Permiso buscarPermisoPorId(Long idPermiso) throws Exception;
	
	Page<Permiso> buscarPermisosPorPaginacion(Integer pagina, Integer tamanioPagina, String campo, boolean asc) throws Exception;
	
	
	Page<Permiso> buscarPermisosPorPaginacionYPorRol(Long idRol, Integer pagina, Integer tamanioPagina, String campo, boolean asc) throws Exception;
	
}

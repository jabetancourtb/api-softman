package com.softman.service;

import org.springframework.data.domain.Page;

import com.softman.entity.Especialidad;

public interface EspecialidadService {

	Especialidad guardarEspecialidad(Especialidad especialidad) throws Exception;
	
	void eliminarEspecialidadPorId(Long idEspecialidad) throws Exception;
	
	Especialidad buscarEspecialidadPorId(Long idEspecialidad) throws Exception;
	
	Page<Especialidad> buscarEspecialidadesPorPaginacion(Integer pagina, Integer tamanioPagina, String campo, boolean asc) throws Exception;
	
}

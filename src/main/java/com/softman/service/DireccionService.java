package com.softman.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.softman.entity.Direccion;

public interface DireccionService {

	Direccion guardarDireccion(Direccion direccion) throws Exception;
	
	List<Direccion> guardarDirecciones(List<Direccion> direcciones) throws Exception;
	
	void eliminarDireccionPorId(Long idDireccion) throws Exception;
	
	Direccion buscarDireccionPorId(Long idDireccion) throws Exception;
	
	
	Page<Direccion> buscarDireccionesPorPaginacionYIdUsuario(Long idUsuario, Integer pagina, Integer tamanioPagina, String campo, boolean asc) throws Exception;
	
}

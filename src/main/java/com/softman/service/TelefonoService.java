package com.softman.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.softman.entity.Telefono;

public interface TelefonoService {

	Telefono guardarTelefono(Telefono telefono) throws Exception;
	
	List<Telefono> guardarTelefonos(List<Telefono> telefonos) throws Exception;
	
	void eliminarTelefonoPorId(Long idTelefono) throws Exception;
	
	Telefono buscarTelefonoPorId(Long idTelefono) throws Exception;
	
	
	Page<Telefono> buscarTelefonosPorPaginacionYPorIdUsuario(Long idUsuario, Integer pagina, Integer tamanioPagina, String campo, boolean asc) throws Exception;
	
}

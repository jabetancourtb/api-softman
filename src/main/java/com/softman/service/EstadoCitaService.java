package com.softman.service;

import org.springframework.data.domain.Page;

import com.softman.entity.EstadoCita;

public interface EstadoCitaService {

	EstadoCita guardarEstadoCita(EstadoCita estadoCita) throws Exception;
	
	void eliminarEstadoCitaPorId(Long idEstadoCita) throws Exception;
	
	EstadoCita buscarEstadoCitaPorId(Long idEstadoCita) throws Exception;
	
	EstadoCita buscarEstadoCitaPorEstado(String nombreEstado) throws Exception;
	
	Page<EstadoCita> buscarEstadosCitasPorPaginacion(Integer pagina, Integer tamanioPagina, String campo, boolean asc) throws Exception;
	
}

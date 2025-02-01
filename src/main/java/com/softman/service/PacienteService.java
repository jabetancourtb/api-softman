package com.softman.service;

import org.springframework.data.domain.Page;

import com.softman.entity.Paciente;

public interface PacienteService {

	Paciente guardarPaciente(Paciente paciente) throws Exception;
	
	void eliminarPacientePorId(Long idPaciente) throws Exception;
	
	Paciente buscarPacientePorId(Long idPaciente) throws Exception;
	
	Page<Paciente> buscarPacientesPorPaginacion(Integer pagina, Integer tamanioPagina, String campo, boolean asc) throws Exception;
	
}

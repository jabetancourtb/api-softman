package com.softman.service;

import org.springframework.data.domain.Page;

import com.softman.entity.Medico;

public interface MedicoService {

	Medico guardarMedico(Medico medico) throws Exception;
	
	void eliminarMedicoPorId(Long idMedico) throws Exception;
	
	Medico buscarMedicoPorId(Long idMedico) throws Exception;
	
	Page<Medico> buscarMedicosPorPaginacion(Integer pagina, Integer tamanioPagina, String campo, boolean asc) throws Exception;
	
	Page<Medico> buscarMedicosPorPaginacionPorIdCentroMedico(Long idCentroMedico, Integer pagina, Integer tamanioPagina, String campo, boolean asc) throws Exception;
	
}

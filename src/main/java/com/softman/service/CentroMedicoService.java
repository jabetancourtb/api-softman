package com.softman.service;

import org.springframework.data.domain.Page;

import com.softman.entity.CentroMedico;

public interface CentroMedicoService {

	CentroMedico guardarCentroMedico(CentroMedico centroMedico) throws Exception;
	
	void eliminarCentroMedicoPorId(Long idCentroMedico) throws Exception;
	
	CentroMedico buscarCentroMedicoPorId(Long idCentroMedico) throws Exception;
	
	Page<CentroMedico> buscarCentrosMedicosPorPaginacion(Integer pagina, Integer tamanioPagina, String campo, boolean asc) throws Exception;
	
}

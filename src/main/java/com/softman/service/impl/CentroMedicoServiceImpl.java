package com.softman.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.softman.entity.CentroMedico;
import com.softman.repository.CentroMedicoJpaRepository;
import com.softman.service.CentroMedicoService;

@Service
public class CentroMedicoServiceImpl implements CentroMedicoService {
	
	@Autowired
	private CentroMedicoJpaRepository centroCentroMedicoJpaRepository;
	

	@Override
	public CentroMedico guardarCentroMedico(CentroMedico centroMedico) throws Exception {
		return centroCentroMedicoJpaRepository.save(centroMedico);
	}

	
	@Override
	public void eliminarCentroMedicoPorId(Long idCentroMedico) throws Exception {
		centroCentroMedicoJpaRepository.deleteById(idCentroMedico);		
	}

	
	@Override
	public CentroMedico buscarCentroMedicoPorId(Long idCentroMedico) throws Exception {
		return centroCentroMedicoJpaRepository.findById(idCentroMedico).orElse(null);
	}

	
	@Override
	public Page<CentroMedico> buscarCentrosMedicosPorPaginacion(Integer pagina, Integer tamanioPagina, String campo,
			boolean asc) throws Exception {
		Sort sorting = Sort.by(campo);
		
		if(!asc) {
			sorting = Sort.by(campo).descending();
		}

		return centroCentroMedicoJpaRepository.findAll(PageRequest.of(pagina, tamanioPagina).withSort(sorting));
	}

}

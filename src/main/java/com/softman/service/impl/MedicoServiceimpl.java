package com.softman.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.softman.entity.Medico;
import com.softman.repository.MedicoJpaRepository;
import com.softman.service.MedicoService;


@Service
public class MedicoServiceimpl implements MedicoService {

	
	@Autowired
	private MedicoJpaRepository medicoJpaRepository;
	

	@Override
	public Medico guardarMedico(Medico medico) throws Exception {
		return medicoJpaRepository.save(medico);
	}

	
	@Override
	public void eliminarMedicoPorId(Long idMedico) throws Exception {
		medicoJpaRepository.deleteById(idMedico);		
	}

	
	@Override
	public Medico buscarMedicoPorId(Long idMedico) throws Exception {
		return medicoJpaRepository.findById(idMedico).orElse(null);
	}
	

	@Override
	public Page<Medico> buscarMedicosPorPaginacion(Integer pagina, Integer tamanioPagina, String campo,
			boolean asc) throws Exception {
		Sort sorting = Sort.by(campo);
		
		if(!asc) {
			sorting = Sort.by(campo).descending();
		}

		return medicoJpaRepository.findAll(PageRequest.of(pagina, tamanioPagina).withSort(sorting));
	}
	
	
	@Override
	public Page<Medico> buscarMedicosPorPaginacionPorIdCentroMedico(Long idCentroMedico, 
			Integer pagina, Integer tamanioPagina, String campo,boolean asc) throws Exception {
		Sort sorting = Sort.by(campo);
		
		if(!asc) {
			sorting = Sort.by(campo).descending();
		}

		return medicoJpaRepository.buscarMedicosPorPaginacionPorIdCentroMedico(idCentroMedico, PageRequest.of(pagina, tamanioPagina).withSort(sorting));
	}

}

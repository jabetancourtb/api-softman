package com.softman.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.softman.entity.EstadoCita;
import com.softman.repository.EstadoCitaJpaRepository;
import com.softman.service.EstadoCitaService;

@Service
public class EstadoCitaServiceImpl implements EstadoCitaService {

	
	@Autowired
	private EstadoCitaJpaRepository estadoCitaJpaRepository;
	

	@Override
	public EstadoCita guardarEstadoCita(EstadoCita estadoCita) throws Exception {
		return estadoCitaJpaRepository.save(estadoCita);
	}

	
	@Override
	public void eliminarEstadoCitaPorId(Long idEstadoCita) throws Exception {
		estadoCitaJpaRepository.deleteById(idEstadoCita);		
	}

	
	@Override
	public EstadoCita buscarEstadoCitaPorId(Long idEstadoCita) throws Exception {
		return estadoCitaJpaRepository.findById(idEstadoCita).orElse(null);
	}
	
	
	@Override
	public EstadoCita buscarEstadoCitaPorEstado(String estado) throws Exception {
		return estadoCitaJpaRepository.findEstadoCitaByEstado(estado);
	}

	
	@Override
	public Page<EstadoCita> buscarEstadosCitasPorPaginacion(Integer pagina, Integer tamanioPagina, String campo,
			boolean asc) throws Exception {
		Sort sorting = Sort.by(campo);
		
		if(!asc) {
			sorting = Sort.by(campo).descending();
		}

		return estadoCitaJpaRepository.findAll(PageRequest.of(pagina, tamanioPagina).withSort(sorting));
	}

}

package com.softman.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.softman.entity.Paciente;
import com.softman.repository.PacienteJpaRepository;
import com.softman.service.PacienteService;


@Service
public class PacienteServiceImpl implements PacienteService {

	
	@Autowired
	private PacienteJpaRepository pacienteJpaRepository;
	

	@Override
	public Paciente guardarPaciente(Paciente paciente) throws Exception {
		return pacienteJpaRepository.save(paciente);
	}

	
	@Override
	public void eliminarPacientePorId(Long idPaciente) throws Exception {
		pacienteJpaRepository.deleteById(idPaciente);		
	}

	
	@Override
	public Paciente buscarPacientePorId(Long idPaciente) throws Exception {
		return pacienteJpaRepository.findById(idPaciente).orElse(null);
	}
	

	@Override
	public Page<Paciente> buscarPacientesPorPaginacion(Integer pagina, Integer tamanioPagina, String campo,
			boolean asc) throws Exception {
		Sort sorting = Sort.by(campo);
		
		if(!asc) {
			sorting = Sort.by(campo).descending();
		}

		return pacienteJpaRepository.findAll(PageRequest.of(pagina, tamanioPagina).withSort(sorting));
	}
	
	
}

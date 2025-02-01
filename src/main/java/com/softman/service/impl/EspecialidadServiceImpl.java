package com.softman.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.softman.entity.Especialidad;
import com.softman.repository.EspecialidadJpaRepository;
import com.softman.service.EspecialidadService;


@Service
public class EspecialidadServiceImpl implements EspecialidadService {

	
	@Autowired
	private EspecialidadJpaRepository especialidadJpaRepository;
	

	@Override
	public Especialidad guardarEspecialidad(Especialidad especialidad) throws Exception {
		return especialidadJpaRepository.save(especialidad);
	}

	
	@Override
	public void eliminarEspecialidadPorId(Long idEspecialidad) throws Exception {
		especialidadJpaRepository.deleteById(idEspecialidad);		
	}

	
	@Override
	public Especialidad buscarEspecialidadPorId(Long idEspecialidad) throws Exception {
		return especialidadJpaRepository.findById(idEspecialidad).orElse(null);
	}
	

	
	@Override
	public Page<Especialidad> buscarEspecialidadesPorPaginacion(Integer pagina, Integer tamanioPagina, String campo,
			boolean asc) throws Exception {
		Sort sorting = Sort.by(campo);
		
		if(!asc) {
			sorting = Sort.by(campo).descending();
		}

		return especialidadJpaRepository.findAll(PageRequest.of(pagina, tamanioPagina).withSort(sorting));
	}
	
}

package com.softman.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.softman.entity.Direccion;
import com.softman.repository.DireccionJpaRepository;
import com.softman.service.DireccionService;

@Service
public class DireccionServiceImpl implements DireccionService {
	
	@Autowired
	private DireccionJpaRepository direccionJpaRepository;
	

	@Override
	public Direccion guardarDireccion(Direccion direccion) throws Exception {
		return direccionJpaRepository.save(direccion);
	}
	
	
	@Override
	public List<Direccion> guardarDirecciones(List<Direccion> direcciones) throws Exception {
		return direccionJpaRepository.saveAll(direcciones);
	}

	
	@Override
	public void eliminarDireccionPorId(Long idDireccion) throws Exception {
		direccionJpaRepository.deleteById(idDireccion);		
	}

	
	@Override
	public Direccion buscarDireccionPorId(Long idDireccion) throws Exception {
		return direccionJpaRepository.findById(idDireccion).orElse(null);
	}

	
	@Override
	public Page<Direccion> buscarDireccionesPorPaginacionYIdUsuario(Long idUsuario, Integer pagina, Integer tamanioPagina, String campo,
			boolean asc) throws Exception {
		Sort sorting = Sort.by(campo);
		
		if(!asc) {
			sorting = Sort.by(campo).descending();
		}

		return direccionJpaRepository.buscarDireccionesPorPaginacionYIdUsuario(idUsuario,PageRequest.of(pagina, tamanioPagina).withSort(sorting));
	}
	
}

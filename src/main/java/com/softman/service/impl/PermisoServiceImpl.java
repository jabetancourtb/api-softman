package com.softman.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.softman.entity.Permiso;
import com.softman.repository.PermisoJpaRepository;
import com.softman.service.PermisoService;


@Service
public class PermisoServiceImpl  implements PermisoService {

	
	@Autowired
	private PermisoJpaRepository permisoJpaRepository;
	

	@Override
	public Permiso guardarPermiso(Permiso permiso) throws Exception {
		return permisoJpaRepository.save(permiso);
	}

	
	@Override
	public void eliminarPermisoPorId(Long idPermiso) throws Exception {
		permisoJpaRepository.deleteById(idPermiso);		
	}

	
	@Override
	public Permiso buscarPermisoPorId(Long idPermiso) throws Exception {
		return permisoJpaRepository.findById(idPermiso).orElse(null);
	}
	

	@Override
	public Page<Permiso> buscarPermisosPorPaginacion(Integer pagina, Integer tamanioPagina, String campo,
			boolean asc) throws Exception {
		Sort sorting = Sort.by(campo);
		
		if(!asc) {
			sorting = Sort.by(campo).descending();
		}

		return permisoJpaRepository.findAll(PageRequest.of(pagina, tamanioPagina).withSort(sorting));
	}
	
	
	@Override
	public Page<Permiso> buscarPermisosPorPaginacionYPorRol(Long idRol, Integer pagina, Integer tamanioPagina, String campo,
			boolean asc) throws Exception {
		Sort sorting = Sort.by(campo);
		
		if(!asc) {
			sorting = Sort.by(campo).descending();
		}

		return permisoJpaRepository.buscarPermisosPorPaginacionYPorRol(idRol, PageRequest.of(pagina, tamanioPagina).withSort(sorting));
	}
	
}

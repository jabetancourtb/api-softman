package com.softman.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.softman.entity.Rol;
import com.softman.repository.RolJpaRepository;
import com.softman.service.RolService;

@Service
public class RolServiceImpl implements RolService {

	
	@Autowired
	private RolJpaRepository rolJpaRepository;
	

	@Override
	public Rol guardarRol(Rol rol) throws Exception {
		return rolJpaRepository.save(rol);
	}

	
	@Override
	public void eliminarRolPorId(Long idRol) throws Exception {
		rolJpaRepository.deleteById(idRol);		
	}

	
	@Override
	public Rol buscarRolPorId(Long idRol) throws Exception {
		return rolJpaRepository.findById(idRol).orElse(null);
	}
	
	
	@Override
	public Rol buscarRolPorNombre(String nombre) throws Exception {
		return rolJpaRepository.buscarRolPorNombre(nombre);
	}
	

	@Override
	public Page<Rol> buscarRolesPorPaginacion(Integer pagina, Integer tamanioPagina, String campo,
			boolean asc) throws Exception {
		Sort sorting = Sort.by(campo);
		
		if(!asc) {
			sorting = Sort.by(campo).descending();
		}

		return rolJpaRepository.findAll(PageRequest.of(pagina, tamanioPagina).withSort(sorting));
	}
	
	
	@Override
	public Page<Rol> buscarRolesPorPaginacionYPorIdUsuario(Long idUsuario, Integer pagina, Integer tamanioPagina, String campo,
			boolean asc) throws Exception {
		Sort sorting = Sort.by(campo);
		
		if(!asc) {
			sorting = Sort.by(campo).descending();
		}

		return rolJpaRepository.buscarRolesPorPaginacionPorIdUsuario(idUsuario, PageRequest.of(pagina, tamanioPagina).withSort(sorting));
	}
	
}

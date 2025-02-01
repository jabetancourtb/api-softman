package com.softman.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.softman.entity.Telefono;
import com.softman.repository.TelefonoJpaRepository;
import com.softman.service.TelefonoService;

@Service
public class TelefonoServiceImpl  implements TelefonoService {

	
	@Autowired
	private TelefonoJpaRepository telefonoJpaRepository;
	

	@Override
	public Telefono guardarTelefono(Telefono telefono) throws Exception {
		return telefonoJpaRepository.save(telefono);
	}
	

	@Override
	public List<Telefono> guardarTelefonos(List<Telefono> telefonos) throws Exception {
		return telefonoJpaRepository.saveAll(telefonos);
	}
	
	
	@Override
	public void eliminarTelefonoPorId(Long idTelefono) throws Exception {
		telefonoJpaRepository.deleteById(idTelefono);		
	}

	
	@Override
	public Telefono buscarTelefonoPorId(Long idTelefono) throws Exception {
		return telefonoJpaRepository.findById(idTelefono).orElse(null);
	}
	
	
	@Override
	public Page<Telefono> buscarTelefonosPorPaginacionYPorIdUsuario(Long idUsuario, Integer pagina, Integer tamanioPagina, String campo,
			boolean asc) throws Exception {
		Sort sorting = Sort.by(campo);
		
		if(!asc) {
			sorting = Sort.by(campo).descending();
		}

		return telefonoJpaRepository.buscarTelefonosPorPaginacionYPorIdUsuario(idUsuario, PageRequest.of(pagina, tamanioPagina).withSort(sorting));
	}


}

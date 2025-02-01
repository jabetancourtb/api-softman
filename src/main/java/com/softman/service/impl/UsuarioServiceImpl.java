package com.softman.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.softman.entity.Usuario;
import com.softman.repository.UsuarioJpaRepository;
import com.softman.service.UsuarioService;


@Service
public class UsuarioServiceImpl implements UsuarioService {

	
	@Autowired
	private UsuarioJpaRepository usuarioJpaRepository;
	

	@Override
	public Usuario guardarUsuario(Usuario usuario) throws Exception {
		return usuarioJpaRepository.save(usuario);
	}

	
	@Override
	public void eliminarUsuarioPorId(Long idUsuario) throws Exception {
		usuarioJpaRepository.deleteById(idUsuario);		
	}

	
	@Override
	public Usuario buscarUsuarioPorId(Long idUsuario) throws Exception {
		return usuarioJpaRepository.findById(idUsuario).orElse(null);
	}
	
	@Override
	public Usuario buscarUsuarioPorCorreo(String correo) throws Exception {
		return usuarioJpaRepository.findUserByCorreo(correo);
	}
	
	
	@Override
	public Page<Usuario> buscarUsuariosPorPaginacion(Integer pagina, Integer tamanioPagina, String campo,
			boolean asc) throws Exception {
		Sort sorting = Sort.by(campo);
		
		if(!asc) {
			sorting = Sort.by(campo).descending();
		}

		return usuarioJpaRepository.findAll(PageRequest.of(pagina, tamanioPagina).withSort(sorting));
	}

}

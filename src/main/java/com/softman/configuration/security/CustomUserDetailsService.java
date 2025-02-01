package com.softman.configuration.security;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.softman.entity.Usuario;
import com.softman.repository.UsuarioJpaRepository;


@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final UsuarioJpaRepository usuarioJpaRepository;
	 
	public CustomUserDetailsService(UsuarioJpaRepository usuarioJpaRepository) {
		this.usuarioJpaRepository = usuarioJpaRepository;
	}
	
	
	@Override
	public UserDetails loadUserByUsername(String email) {
		try {
			Usuario usuario = usuarioJpaRepository.findUserByCorreo(email);

			if(usuario == null) {
				throw new UsernameNotFoundException("El correo " + email + " no se ha encontrado");
			}

			if(!usuario.isEstaHabilitado()) {
				throw new BadCredentialsException("El usuario " + usuario.getNombreCompleto() + " no esta habilitado");
			}
			
			return new MyUserDetails(usuario);
		}
		catch(EmptyResultDataAccessException exception) {
			throw new UsernameNotFoundException(email);
		} 
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

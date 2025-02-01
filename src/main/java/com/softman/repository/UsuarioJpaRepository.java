package com.softman.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.softman.entity.Usuario;

public interface UsuarioJpaRepository extends JpaRepository<Usuario, Long> {

	Usuario findUserByCorreo(String correo) throws Exception;
	
}

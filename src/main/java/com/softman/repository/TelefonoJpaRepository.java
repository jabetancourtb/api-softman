package com.softman.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.softman.entity.Telefono;

public interface TelefonoJpaRepository extends JpaRepository<Telefono, Long> {

	@Query(value = "SELECT t.* FROM TELEFONOS t "
			+ " WHERE t.id_usuario = :idUsuario ", nativeQuery = true)
	Page<Telefono> buscarTelefonosPorPaginacionYPorIdUsuario(@Param("idUsuario") Long idUsuario, PageRequest pageRequest) throws Exception;
}

package com.softman.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.softman.entity.Direccion;

public interface DireccionJpaRepository extends JpaRepository<Direccion, Long> {

	@Query(value = "SELECT d.* FROM DIRECCIONES d "
			+ " WHERE d.id_usuario = :idUsuario ", nativeQuery = true)
	Page<Direccion> buscarDireccionesPorPaginacionYIdUsuario(@Param("idUsuario") Long idUsuario, PageRequest pageRequest) throws Exception;
	
}

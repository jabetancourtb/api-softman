package com.softman.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.softman.entity.Permiso;

public interface PermisoJpaRepository extends JpaRepository<Permiso, Long> {

	@Query(value = "SELECT p.* FROM PERMISOS p "
			+ " INNER JOIN ROLES_PERMSOS rp  ON rp.id_permiso = p.id "
			+ " INNER JOIN ROLES r  ON r.id = rp.id_rol "
			+ " WHERE r.id = :idRol ", nativeQuery = true)
	Page<Permiso> buscarPermisosPorPaginacionYPorRol(@Param("idRol") Long idRol, PageRequest pageRequest) throws Exception;
	
}

package com.softman.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.softman.entity.Rol;

public interface RolJpaRepository extends JpaRepository<Rol, Long> {

	
	@Query(value = "SELECT r.* FROM ROLES r "
			+ " WHERE r.nombre = :nombre ", nativeQuery = true)
	Rol buscarRolPorNombre(@Param("nombre") String nombre) throws Exception;
	

	@Query(value = "SELECT r.* FROM ROLES r "
			+ " INNER JOIN USUARIOS_ROLES ur  ON ur.id_rol = r.id "
			+ " INNER JOIN USUARIOS u  ON u.id = ur.id_usuario "
			+ " WHERE u.id = :idUsuario ", nativeQuery = true)
	Page<Rol> buscarRolesPorPaginacionPorIdUsuario(@Param("idUsuario") Long idUsuario, PageRequest pageRequest) throws Exception;
	
}

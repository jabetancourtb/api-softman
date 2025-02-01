package com.softman.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.softman.entity.Medico;

public interface MedicoJpaRepository extends JpaRepository<Medico, Long> {

	@Query(value = "SELECT m.* FROM MEDICOS m "
			+ " INNER JOIN MEDICOS_CENTROS_MEDICOS mcm  ON mcm.id_medico = m.id"
			+ " INNER JOIN CENTROS_MEDICOS cm  ON cm.id = mcm.id_centro_medico"
			+ " WHERE cm.id = :idCentroMedico ", nativeQuery = true)
	Page<Medico> buscarMedicosPorPaginacionPorIdCentroMedico(@Param("idCentroMedico") Long idCentroMedico, PageRequest pageRequest) throws Exception;
	
}

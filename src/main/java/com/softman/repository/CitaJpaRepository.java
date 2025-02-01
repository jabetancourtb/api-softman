package com.softman.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.softman.entity.Cita;

public interface CitaJpaRepository extends JpaRepository<Cita, Long> {

	@Query(value = "SELECT c.* FROM CITAS c "
			+ " WHERE c.id_estado_cita = :idEstadoCita ", nativeQuery = true)
	Page<Cita> buscarCitasPorPaginacionYPorIdEstadoCita(@Param("idEstadoCita") Long idEstadoCita, PageRequest pageRequest) throws Exception;
	

	@Query(value = "SELECT c.* FROM CITAS c "
			+ " WHERE "
			+ " c.id_estado_cita = :idEstadoCita "
			+ " AND c.id_medico = :idMedico ", nativeQuery = true)
	Page<Cita> buscarCitasPorPaginacionPorIdEstadoCitaYPorIdMedico(
			@Param("idEstadoCita") Long idEstadoCita, 
			@Param("idMedico") Long idMedico, 
			PageRequest pageRequest) throws Exception;
	
	
	@Query(value = "SELECT c.* FROM CITAS c "
			+ " WHERE "
			+ " c.id_estado_cita = :idEstadoCita "
			+ " AND c.id_especialidad = :idEspecialidad ", nativeQuery = true)
	Page<Cita> buscarCitasPorPaginacionPorIdEstadoCitaYPorIdEspecialidad(
			@Param("idEstadoCita") Long idEstadoCita, 
			@Param("idEspecialidad") Long idMedico, 
			PageRequest pageRequest) throws Exception;
	
	
	@Query(value = "SELECT c.* FROM CITAS c "
			+ " WHERE "
			+ " c.id_estado_cita = :idEstadoCita "
			+ " AND c.id_medico = :idMedico "
			+ " AND c.id_centro_medico = :idCentroMedico ", nativeQuery = true)
	Page<Cita> buscarCitasPorPaginacionPorIdEstadoCitaPorIdMedicoYPorIdCentroMedico(
			@Param("idEstadoCita") Long idEstadoCita, 
			@Param("idMedico") Long idMedico, 
			@Param("idCentroMedico") Long idCentroMedico, 
			PageRequest pageRequest) throws Exception;
	
	
	@Query(value = "SELECT c.* FROM CITAS c "
			+ " WHERE "
			+ " c.id_estado_cita = :idEstadoCita "
			+ " AND c.id_especialidad = :idEspecialidad "
			+ " AND c.id_centro_medico ", nativeQuery = true)
	Page<Cita> buscarCitasPorPaginacionPorIdEstadoCitaPorIdEspecialidadYPorIdCentroMedico(
			@Param("idEstadoCita") Long idEstadoCita, 
			@Param("idEspecialidad") Long idEspecialidad, 
			@Param("idCentroMedico") Long idCentroMedico, 
			PageRequest pageRequest) throws Exception;
	
	
	@Query(value = "SELECT c.* FROM CITAS c "
			+ " WHERE "
			+ " c.id_estado_cita = :idEstadoCita "
			+ " AND c.id_paciente = :idPaciente ", nativeQuery = true)
	Page<Cita> buscarCitasPorPaginacionPorIdEstadoCitaYPorIdPaciente(
			@Param("idEstadoCita") Long idEstadoCita, 
			@Param("idPaciente") Long idPaciente, 
			PageRequest pageRequest) throws Exception;
	
}

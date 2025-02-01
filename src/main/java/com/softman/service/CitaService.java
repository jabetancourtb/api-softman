package com.softman.service;

import org.springframework.data.domain.Page;

import com.softman.entity.Cita;

public interface CitaService {

	Cita guardarCita(Cita cita) throws Exception;
	
	Cita actualizarEstadoCitaPorId(Cita cita, Long idEstadoCita) throws Exception;
	
	void eliminarCitaPorId(Long idCita) throws Exception;
	
	Cita buscarCitaPorId(Long idCita) throws Exception;

	Page<Cita> buscarCitasPorPaginacionYPorIdEstadoCita(Long idEstadoCita, Integer pagina, Integer tamanioPagina, String campo, boolean asc) throws Exception;
		
	
	// URIConstants.MEDICOS+"/{idMedico}"+URIConstants.ESTADOS_CITAS+"/{idEstadoCita}"+URIConstants.CITAS
	// medicos/{idMedico}/estados-citas/{idEstadoCita}/citas
	Page<Cita> buscarCitasPorPaginacionPorIdEstadoCitaYPorIdMedico(Long idEstadoCita, Long idMedico, Integer pagina, Integer tamanioPagina, String campo, boolean asc) throws Exception;
	
	// URIConstants.ESPECIALIDADES+"/{idEspecialidad}"+URIConstants.ESTADOS_CITAS+"/{idEstadoCita}"+URIConstants.CITAS
	// especialidades/{idEspecialidad}/estados-citas/{idEstadoCita}/citas
	Page<Cita> buscarCitasPorPaginacionPorIdEstadoCitaYPorIdEspecialidad(Long idEstadoCita, Long idEspecialidad, Integer pagina, Integer tamanioPagina, String campo, boolean asc) throws Exception;
	
	// URIConstants.CENTROS_MEDICOS+"/{idCentroMedico}"+URIConstants.MEDICOS+"/{idMedico}"+URIConstants.ESTADOS_CITAS+"/{idEstadoCita}"+URIConstants.CITAS
	// centros-medicos/{idCentroMedico}/medicos/{idMedico}/estados-citas/{idEstadoCita}/citas
	Page<Cita> buscarCitasPorPaginacionPorIdEstadoCitaPorIdMedicoYPorIdCentroMedico(Long idEstadoCita, Long idMedico, Long idCentroMedico, Integer pagina, Integer tamanioPagina, String campo, boolean asc) throws Exception;
	
	// URIConstants.CENTROS_MEDICOS+"/{idCentroMedico}"+URIConstants.ESPECIALIDADES+"/{idEspecialidad}"+URIConstants.ESTADOS_CITAS+"/{idEstadoCita}"+URIConstants.CITAS
	// centros-medicos/{idCentroMedico}/especialidades/{idEspecialidad}/estados-citas/{idEstadoCita}/citas
	Page<Cita> buscarCitasPorPaginacionPorIdEstadoCitaPorIdEspecialidadYPorIdCentroMedico(Long idEstadoCita, Long idEspecialidad, Long idCentroMedico, Integer pagina, Integer tamanioPagina, String campo, boolean asc) throws Exception;
	
	
	// URIConstants.PACIENTES+"/{idPaciente}"+URIConstants.ESTADOS_CITAS+"/{idEstadoCita}"+URIConstants.CITAS
	// pacientes/{idPaciente}/estados-citas/{idEstadoCita}/citas
	Page<Cita> buscarCitasPorPaginacionPorIdEstadoCitaYPorIdPaciente(Long idPaciente, Long idEstadoCita, Integer pagina, Integer tamanioPagina, String campo, boolean asc) throws Exception;
	
}

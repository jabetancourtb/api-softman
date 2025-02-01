package com.softman.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.softman.entity.Cita;
import com.softman.entity.EstadoCita;
import com.softman.entity.Paciente;
import com.softman.enumeration.EstadoCitaEnum;
import com.softman.exception.BusinessException;
import com.softman.repository.CitaJpaRepository;
import com.softman.service.CitaService;
import com.softman.service.EstadoCitaService;
import com.softman.service.PacienteService;


@Service
public class CitaServiceImpl implements CitaService {
	
	@Autowired
	private CitaJpaRepository citaJpaRepository;
	
	@Autowired
	private EstadoCitaService estadoCitaService;
	
	@Autowired
	private PacienteService pacienteService;
	
	
	@Override
	public Cita guardarCita(Cita cita) throws Exception {

		if(yaExisteCitaAsignada(cita)) {
			throw new BusinessException("La franja para esta cita ya esta asignada");
		}
		
		validarPacienteNuevaCita(cita);
		
		return citaJpaRepository.save(cita);
	}
	
	
	private boolean yaExisteCitaAsignada(Cita nuevaCita) throws Exception {
		
		EstadoCita estadoCitaBD = estadoCitaService.buscarEstadoCitaPorEstado(EstadoCitaEnum.ASIGNADA.getValue());
		
		Page<Cita> citasAsignadasBD = buscarCitasPorPaginacionPorIdEstadoCitaPorIdMedicoYPorIdCentroMedico(
				estadoCitaBD.getId(), 
				nuevaCita.getMedico().getId(),
				nuevaCita.getCentroMedico().getId(),
				0, 
				Integer.MAX_VALUE, 
				"fecha_hora", 
				false);
				
		Optional<Cita> citasAsignadas = citasAsignadasBD.getContent()
			.stream()
			.parallel()
			.filter(citaBD -> citaBD.getFechaHora().compareTo(nuevaCita.getFechaHora()) == 0)
			.findAny();
		
		boolean idEstadoCitaNuevaEsDistintoAAsignado = nuevaCita.getEstadoCita().getId().equals(estadoCitaBD.getId());
				
		return citasAsignadas.isPresent() && idEstadoCitaNuevaEsDistintoAAsignado;
	}
	
	
	private void validarPacienteNuevaCita(Cita nuevaCita) throws Exception {
		
		if(nuevaCita.getPaciente() == null || nuevaCita.getPaciente().getId() == null) {
			nuevaCita.setPaciente(null);
		}
		else {
			Paciente pacienteBD = pacienteService.buscarPacientePorId(nuevaCita.getPaciente().getId());
			
			if(pacienteBD == null) {
				throw new BusinessException("El idPaciente ingresado no existe");
			}
			
			nuevaCita.setPaciente(pacienteBD);
		}	
	}
	
	
	@Override
	public Cita actualizarEstadoCitaPorId(Cita cita, Long idEstadoCita) throws Exception {
		
		EstadoCita estadoCitaBD = estadoCitaService.buscarEstadoCitaPorId(idEstadoCita);
		
		if(estadoCitaBD == null) {
			throw new BusinessException("El idEstadoCita ingresado no existe");
		}
		
		cita.setEstadoCita(estadoCitaBD);
		
		return cita;
	}

	
	@Override
	public void eliminarCitaPorId(Long idCita) throws Exception {
		citaJpaRepository.deleteById(idCita);		
	}

	
	@Override
	public Cita buscarCitaPorId(Long idCita) throws Exception {
		return citaJpaRepository.findById(idCita).orElse(null);
	}

	
	@Override
	public Page<Cita> buscarCitasPorPaginacionYPorIdEstadoCita(Long idEstadoCita, Integer pagina, Integer tamanioPagina, String campo,
			boolean asc) throws Exception {
		Sort sorting = Sort.by(campo);
		
		if(!asc) {
			sorting = Sort.by(campo).descending();
		}

		return citaJpaRepository.buscarCitasPorPaginacionYPorIdEstadoCita(idEstadoCita, PageRequest.of(pagina, tamanioPagina).withSort(sorting));
	}
	
		
	@Override
	public Page<Cita> buscarCitasPorPaginacionPorIdEstadoCitaYPorIdMedico(Long idEstadoCita, Long idMedico, Integer pagina, Integer tamanioPagina, String campo,
			boolean asc) throws Exception {
		Sort sorting = Sort.by(campo);
		
		if(!asc) {
			sorting = Sort.by(campo).descending();
		}

		return citaJpaRepository.buscarCitasPorPaginacionPorIdEstadoCitaYPorIdMedico(idEstadoCita, idMedico, PageRequest.of(pagina, tamanioPagina).withSort(sorting));
	}
	
	
	@Override
	public Page<Cita> buscarCitasPorPaginacionPorIdEstadoCitaYPorIdEspecialidad(Long idEstadoCita, Long idEspecialidad, Integer pagina, Integer tamanioPagina, String campo,
			boolean asc) throws Exception {
		Sort sorting = Sort.by(campo);
		
		if(!asc) {
			sorting = Sort.by(campo).descending();
		}

		return citaJpaRepository.buscarCitasPorPaginacionPorIdEstadoCitaYPorIdEspecialidad(idEstadoCita, idEspecialidad, PageRequest.of(pagina, tamanioPagina).withSort(sorting));
	}
	
	
	@Override
	public Page<Cita> buscarCitasPorPaginacionPorIdEstadoCitaPorIdMedicoYPorIdCentroMedico(Long idEstadoCita,
			Long idMedico, Long idCentroMedico, Integer pagina, Integer tamanioPagina, String campo, boolean asc)
			throws Exception {
		Sort sorting = Sort.by(campo);
		
		if(!asc) {
			sorting = Sort.by(campo).descending();
		}

		return citaJpaRepository.buscarCitasPorPaginacionPorIdEstadoCitaPorIdMedicoYPorIdCentroMedico(idEstadoCita, idMedico, idCentroMedico, PageRequest.of(pagina, tamanioPagina).withSort(sorting));
	}


	@Override
	public Page<Cita> buscarCitasPorPaginacionPorIdEstadoCitaPorIdEspecialidadYPorIdCentroMedico(
			Long idEstadoCita, Long idEspecialidad, Long idCentroMedico, Integer pagina,
			Integer tamanioPagina, String campo, boolean asc) throws Exception {
		Sort sorting = Sort.by(campo);
		
		if(!asc) {
			sorting = Sort.by(campo).descending();
		}

		return citaJpaRepository.buscarCitasPorPaginacionPorIdEstadoCitaPorIdMedicoYPorIdCentroMedico(idEstadoCita, idEspecialidad, idCentroMedico, PageRequest.of(pagina, tamanioPagina).withSort(sorting));
	}
	
	
	@Override
	public Page<Cita> buscarCitasPorPaginacionPorIdEstadoCitaYPorIdPaciente(Long idEstadoCita, Long idPaciente, Integer pagina, Integer tamanioPagina, String campo,
			boolean asc) throws Exception {
		Sort sorting = Sort.by(campo);
		
		if(!asc) {
			sorting = Sort.by(campo).descending();
		}

		return citaJpaRepository.buscarCitasPorPaginacionPorIdEstadoCitaYPorIdPaciente(idEstadoCita, idPaciente, PageRequest.of(pagina, tamanioPagina).withSort(sorting));
	}


}

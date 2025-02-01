package com.softman.mapper;

import java.util.List;
import org.mapstruct.Mapper;

import com.softman.dto.CitaDTO;
import com.softman.entity.Cita;

@Mapper(componentModel = "spring",
uses = {
		EstadoCitaMapper.class,
		CentroMedicoMapper.class,
		EspecialidadMapper.class,
		MedicoMapper.class,
		PacienteMapper.class
   }
)
public interface CitaMapper {


	CitaDTO citaToCitaDTO(Cita cita) throws Exception;
	
	Cita citaDTOToCita(CitaDTO citaDTO) throws Exception;
	
	
	List<CitaDTO> citaListToCitaDTOList(List<Cita> citaList) throws Exception;
	
	List<Cita> citaDTOListToCitaList(List<CitaDTO> citaDTOList) throws Exception;
	
}

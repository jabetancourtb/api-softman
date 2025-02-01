package com.softman.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.softman.dto.PacienteDTO;
import com.softman.entity.Paciente;


@Mapper(componentModel = "spring")
public interface PacienteMapper {

	// Paciente to PacienteDTO
	
	@Mappings({
		@Mapping(source = "usuario.documento", target = "documento"),
		@Mapping(source = "usuario.primerNombre", target = "primerNombre"),
		@Mapping(source = "usuario.segundoNombre", target = "segundoNombre"),
		@Mapping(source = "usuario.primerApellido", target = "primerApellido"),
		@Mapping(source = "usuario.segundoApellido", target = "segundoApellido"),
		@Mapping(source = "usuario.id", target = "idUsuario")
	})
	PacienteDTO pacienteToPacienteDTO(Paciente paciente) throws Exception;
	
	List<PacienteDTO> pacienteListToPacienteDTOList(List<Paciente> pacienteList) throws Exception;
	

	
	// PacienteDTO to Paciente
	
	@Mappings({
		@Mapping(source = "documento", target = "usuario.documento"),
		@Mapping(source = "primerNombre", target = "usuario.primerNombre"),
		@Mapping(source = "segundoNombre", target = "usuario.segundoNombre"),
		@Mapping(source = "primerApellido", target = "usuario.primerApellido"),
		@Mapping(source = "segundoApellido", target = "usuario.segundoApellido"),
		@Mapping(source = "idUsuario", target = "usuario.id"),
		@Mapping(target = "citas", ignore = true)
	})
	Paciente pacienteDTOToPaciente(PacienteDTO pacienteDTO) throws Exception;

	List<Paciente> pacienteDTOListToPacienteList(List<PacienteDTO> pacienteDTOList) throws Exception;
	
}

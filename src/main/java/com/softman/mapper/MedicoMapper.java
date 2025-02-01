package com.softman.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.softman.dto.MedicoDTO;
import com.softman.entity.Medico;

@Mapper(componentModel = "spring")
public interface MedicoMapper {

	// Medico to MedicoDTO
	
	@Mappings({
		@Mapping(source = "usuario.documento", target = "documento"),
		@Mapping(source = "usuario.primerNombre", target = "primerNombre"),
		@Mapping(source = "usuario.segundoNombre", target = "segundoNombre"),
		@Mapping(source = "usuario.primerApellido", target = "primerApellido"),
		@Mapping(source = "usuario.segundoApellido", target = "segundoApellido"),
		@Mapping(source = "usuario.id", target = "idUsuario")
	})
	MedicoDTO medicoToMedicoDTO(Medico medico) throws Exception;
	
	List<MedicoDTO> medicoListToMedicoDTOList(List<Medico> medicoList) throws Exception;
	

	
	// MedicoDTO to Medico
	
	@Mappings({
		@Mapping(source = "documento", target = "usuario.documento"),
		@Mapping(source = "primerNombre", target = "usuario.primerNombre"),
		@Mapping(source = "segundoNombre", target = "usuario.segundoNombre"),
		@Mapping(source = "primerApellido", target = "usuario.primerApellido"),
		@Mapping(source = "segundoApellido", target = "usuario.segundoApellido"),
		@Mapping(source = "idUsuario", target = "usuario.id"),
		@Mapping(target = "centrosMedicos", ignore = true),
		@Mapping(target = "citas", ignore = true)
	})
	Medico medicoDTOToMedico(MedicoDTO medicoDTO) throws Exception;

	List<Medico> medicoDTOListToMedicoList(List<MedicoDTO> medicoDTOList) throws Exception;
	
}

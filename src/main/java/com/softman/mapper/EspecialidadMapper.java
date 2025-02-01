package com.softman.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.softman.dto.EspecialidadDTO;
import com.softman.entity.Especialidad;

@Mapper(componentModel = "spring")
public interface EspecialidadMapper {

	// Especialidad to EspecialidadDTO
	
	EspecialidadDTO especialidadToEspecialidadDTO(Especialidad especialidad) throws Exception;
	
	List<EspecialidadDTO> especialidadListToEspecialidadDTOList(List<Especialidad> especialidadList) throws Exception;
	

	
	// EspecialidadDTO to Especialidad
	@Mapping(target = "citas", ignore = true)
	Especialidad especialidadDTOToEspecialidad(EspecialidadDTO especialidadDTO) throws Exception;

	List<Especialidad> especialidadDTOListToEspecialidadList(List<EspecialidadDTO> especialidadDTOList) throws Exception;
	
}

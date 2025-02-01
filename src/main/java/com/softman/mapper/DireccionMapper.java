package com.softman.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.softman.dto.DireccionDTO;
import com.softman.entity.Direccion;

@Mapper(componentModel = "spring")
public interface DireccionMapper {

	// Direccion to DireccionDTO
	
	@Mapping(source = "usuario.id", target = "idUsuario")
	DireccionDTO direccionToDireccionDTO(Direccion direccion) throws Exception;
	
	List<DireccionDTO> direccionListToDireccionDTOList(List<Direccion> direccionList) throws Exception;
	

	
	// DireccionDTO to Direccion
	
	@Mapping(source = "idUsuario", target = "usuario.id")
	Direccion direccionDTOToDireccion(DireccionDTO direccionDTO) throws Exception;

	List<Direccion> direccionDTOListToDireccionList(List<DireccionDTO> direccionDTOList) throws Exception;
	
}

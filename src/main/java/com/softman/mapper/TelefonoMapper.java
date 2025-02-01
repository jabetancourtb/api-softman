package com.softman.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.softman.dto.TelefonoDTO;
import com.softman.entity.Telefono;


@Mapper(componentModel = "spring")
public interface TelefonoMapper {

	// Telefono to TelefonoDTO
	
	@Mapping(source = "usuario.id", target = "idUsuario")
	TelefonoDTO telefonoToTelefonoDTO(Telefono telefono) throws Exception;
	
	List<TelefonoDTO> telefonoListToTelefonoDTOList(List<Telefono> telefonoList) throws Exception;
	

	
	// TelefonoDTO to Telefono
	
	@Mapping(source = "idUsuario", target = "usuario.id")
	Telefono telefonoDTOToTelefono(TelefonoDTO telefonoDTO) throws Exception;

	List<Telefono> telefonoDTOListToTelefonoList(List<TelefonoDTO> telefonoDTOList) throws Exception;
		
}

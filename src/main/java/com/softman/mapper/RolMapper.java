package com.softman.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.softman.dto.RolDTO;
import com.softman.entity.Rol;


@Mapper(componentModel = "spring")
public interface RolMapper {

	// Rol to RolDTO
	
	RolDTO rolToRolDTO(Rol rol) throws Exception;
	
	List<RolDTO> rolListToRolDTOList(List<Rol> rolList) throws Exception;
	

	
	// RolDTO to Rol
	
	Rol rolDTOToRol(RolDTO rolDTO) throws Exception;

	List<Rol> rolDTOListToRolList(List<RolDTO> rolDTOList) throws Exception;
	
}

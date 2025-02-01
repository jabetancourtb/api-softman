package com.softman.mapper;

import java.util.List;

import org.mapstruct.Mapper;


import com.softman.dto.PermisoDTO;
import com.softman.entity.Permiso;


@Mapper(componentModel = "spring")
public interface PermisoMapper {

	// Permiso to PermisoDTO
	
	PermisoDTO permisoToPermisoDTO(Permiso permiso) throws Exception;
	
	List<PermisoDTO> permisoListToPermisoDTOList(List<Permiso> permisoList) throws Exception;
	

	
	// PermisoDTO to Permiso
	
	Permiso permisoDTOToPermiso(PermisoDTO permisoDTO) throws Exception;

	List<Permiso> permisoDTOListToPermisoList(List<PermisoDTO> permisoDTOList) throws Exception;
}

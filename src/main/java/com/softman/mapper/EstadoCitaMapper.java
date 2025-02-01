package com.softman.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.softman.dto.EstadoCitaDTO;
import com.softman.entity.EstadoCita;

@Mapper(componentModel = "spring")
public interface EstadoCitaMapper {

	// EstadoCita to EstadoCitaDTO
	
	EstadoCitaDTO estadoCitaToEstadoCitaDTO(EstadoCita estadoCita) throws Exception;
	
	List<EstadoCitaDTO> estadoCitaListToEstadoCitaDTOList(List<EstadoCita> estadoCitaList) throws Exception;
	

	
	// EstadoCitaDTO to EstadoCita
	
	EstadoCita estadoCitaDTOToEstadoCita(EstadoCitaDTO estadoCitaDTO) throws Exception;

	List<EstadoCita> estadoCitaDTOListToEstadoCitaList(List<EstadoCitaDTO> estadoCitaDTOList) throws Exception;
	
}

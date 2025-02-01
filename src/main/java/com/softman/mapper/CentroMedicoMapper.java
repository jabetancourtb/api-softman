package com.softman.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.softman.dto.CentroMedicoDTO;
import com.softman.entity.CentroMedico;


@Mapper(componentModel = "spring")
public interface CentroMedicoMapper {

	// CentroMedico to CentroMedicoDTO
	
	CentroMedicoDTO centroMedicoToCentroMedicoDTO(CentroMedico centroMedico) throws Exception;
	
	List<CentroMedicoDTO> centroMedicoListToCentroMedicoDTOList(List<CentroMedico> centroMedicoList) throws Exception;
	

	
	// CentroMedicoDTO to CentroMedico
	
	CentroMedico centroMedicoDTOToCentroMedico(CentroMedicoDTO centroMedicoDTO) throws Exception;

	List<CentroMedico> centroMedicoDTOListToCentroMedicoList(List<CentroMedicoDTO> centroMedicoDTOList) throws Exception;
	
}

package com.softman.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.softman.dto.TipoDocumentoDTO;
import com.softman.entity.TipoDocumento;

@Mapper(componentModel = "spring")
public interface TipoDocumentoMapper {

	// TipoDocumento to TipoDocumentoDTO
	
	TipoDocumentoDTO tipoDocumentoToTipoDocumentoDTO(TipoDocumento tipoDocumento) throws Exception;
	
	List<TipoDocumentoDTO> tipoDocumentoListToTipoDocumentoDTOList(List<TipoDocumento> tipoDocumentoList) throws Exception;
	

	
	// TipoDocumentoDTO to TipoDocumento
	
	TipoDocumento tipoDocumentoDTOToTipoDocumento(TipoDocumentoDTO tipoDocumentoDTO) throws Exception;

	List<TipoDocumento> tipoDocumentoDTOListToTipoDocumentoList(List<TipoDocumentoDTO> tipoDocumentoDTOList) throws Exception;
	
}

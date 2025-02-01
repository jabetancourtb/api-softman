package com.softman.service;

import org.springframework.data.domain.Page;

import com.softman.entity.TipoDocumento;

public interface TipoDocumentoService {

	TipoDocumento guardarTipoDocumento(TipoDocumento tipoDocumento) throws Exception;
	
	void eliminarTipoDocumentoPorId(Long idTipoDocumento) throws Exception;
	
	TipoDocumento buscarTipoDocumentoPorId(Long idTipoDocumento) throws Exception;
	
	Page<TipoDocumento> buscarTipoDocumentosPorPaginacion(Integer pagina, Integer tamanioPagina, String campo, boolean asc) throws Exception;
	
}

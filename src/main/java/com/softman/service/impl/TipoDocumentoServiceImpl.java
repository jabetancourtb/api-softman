package com.softman.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.softman.entity.TipoDocumento;
import com.softman.repository.TipoDocumentoJpaRepository;
import com.softman.service.TipoDocumentoService;


@Service
public class TipoDocumentoServiceImpl implements TipoDocumentoService {

	
	@Autowired
	private TipoDocumentoJpaRepository tipoDocumentoJpaRepository;
	

	@Override
	public TipoDocumento guardarTipoDocumento(TipoDocumento tipoDocumento) throws Exception {
		return tipoDocumentoJpaRepository.save(tipoDocumento);
	}

	
	@Override
	public void eliminarTipoDocumentoPorId(Long idTipoDocumento) throws Exception {
		tipoDocumentoJpaRepository.deleteById(idTipoDocumento);		
	}

	
	@Override
	public TipoDocumento buscarTipoDocumentoPorId(Long idTipoDocumento) throws Exception {
		return tipoDocumentoJpaRepository.findById(idTipoDocumento).orElse(null);
	}
	
	
	@Override
	public Page<TipoDocumento> buscarTipoDocumentosPorPaginacion(Integer pagina, Integer tamanioPagina, String campo,
			boolean asc) throws Exception {
		Sort sorting = Sort.by(campo);
		
		if(!asc) {
			sorting = Sort.by(campo).descending();
		}

		return tipoDocumentoJpaRepository.findAll(PageRequest.of(pagina, tamanioPagina).withSort(sorting));
	}
	
}

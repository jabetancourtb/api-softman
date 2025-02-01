package com.softman.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.softman.entity.TipoDocumento;

public interface TipoDocumentoJpaRepository extends JpaRepository<TipoDocumento, Long> {

}

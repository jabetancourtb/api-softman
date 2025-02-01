package com.softman.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Entity
@Table(name = "tipos_documento")
@Getter 
@Setter
@SuperBuilder
@NoArgsConstructor
public class TipoDocumento extends BaseEntityListener {
	
	private static final long serialVersionUID = 1L; 
	
	@Column(name = "codigo_documento", nullable = false, unique = true)
	private String codigoDocumento; 
	
	@Column(name = "nombre_documento", nullable = false)
	private String nombreDocumento;
	
}

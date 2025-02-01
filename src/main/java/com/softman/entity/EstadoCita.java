package com.softman.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Entity
@Table(name = "estados_citas")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class EstadoCita extends BaseEntityListener {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "estado", nullable = false)
	private String estado;
	
	@Column(name = "descripcion", nullable = false)
	private String descripcion;
	
}

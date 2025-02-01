package com.softman.entity;

import java.util.Set;
import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "centros_medicos")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class CentroMedico  extends BaseEntityListener {
	
	private static final long serialVersionUID = 1L; 
	
	@Column(name = "nombre", nullable = false, unique = true)
	private String nombre; 
	
	@Column(name = "direccion", nullable = false)
	private String direccion;
	
	
	@OneToMany(mappedBy = "centroMedico", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	@Builder.Default
	private Set<Cita> citas = new HashSet<>();
	
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "medicos_centros_medicos", joinColumns = @JoinColumn(name = "id_centro_medico", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "id_medico", referencedColumnName = "id"))
	@JsonBackReference
	@Builder.Default
	private Set<Medico> medicos = new HashSet<>();
	
}

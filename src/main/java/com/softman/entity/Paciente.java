package com.softman.entity;

import java.util.Set;

import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Entity
@Table(name = "pacientes")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Paciente extends BaseEntityListener {
	
	private static final long serialVersionUID = 1L;
	
	
	@OneToOne
	@JoinColumn(name = "id_usuario", referencedColumnName = "id", nullable = false)
	private Usuario usuario;
	
		
	@OneToMany(mappedBy = "paciente", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	@Builder.Default
	private Set<Cita> citas = new HashSet<>();
	
}

package com.softman.entity;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Entity
@Table(name = "permisos")
@Getter 
@Setter
@SuperBuilder
@NoArgsConstructor
public class Permiso extends BaseEntityListener implements GrantedAuthority {

	private static final long serialVersionUID = 1L; 
	
	
	@Column(name = "nombre", nullable = false)
	private String nombre;
	
	@Column(name = "descripcion", nullable = false)
	private String descripcion;
	
	
	@Override
	public String getAuthority() {
		return nombre;
	}
	
	
	@ManyToMany
	@JoinTable(name = "roles_permisos", joinColumns = @JoinColumn(name = "id_permiso", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "id_rol", referencedColumnName = "id"))
	@JsonBackReference
	@Builder.Default
	private Set<Rol> roles = new HashSet<>();
	
}

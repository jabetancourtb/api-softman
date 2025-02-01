package com.softman.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "roles")
@Getter 
@Setter
@SuperBuilder
@NoArgsConstructor
public class Rol extends BaseEntityListener implements GrantedAuthority {

	private static final long serialVersionUID = 1L; 
	
	
	@Column(name = "nombre", nullable = false)
	private String nombre;
	
	@Column(name = "descripcion", nullable = false)
	private String descripcion;
	
	
	public List<SimpleGrantedAuthority> getAuthorities() {
	    var authorities = getPermisos()
	            .stream()
	            .map(permission -> new SimpleGrantedAuthority(permission.getNombre()))
	            .collect(Collectors.toList());
	    
	    authorities.add(new SimpleGrantedAuthority(this.nombre));
	    
	    return authorities;
   	}
	 
	
	@Override
	public String getAuthority() {
		return nombre;
	}
	 
	
	@ManyToMany
	@JoinTable(name = "usuarios_roles", joinColumns = @JoinColumn(name = "id_rol", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "id_usuario", referencedColumnName = "id"))
	@JsonBackReference
	@Builder.Default
	private Set<Usuario> usuarios = new HashSet<>();
	
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "roles_permisos", joinColumns = @JoinColumn(name = "id_rol", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "id_permiso", referencedColumnName = "id"))
	@JsonBackReference
	@Builder.Default
	private Set<Permiso> permisos = new HashSet<>();

}

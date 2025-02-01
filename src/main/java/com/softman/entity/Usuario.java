package com.softman.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Entity
@Table(name = "usuarios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "guid")
public class Usuario extends BaseEntityListener {

	private static final long serialVersionUID = 1L;

	@Column(name = "documento", nullable = false, unique = true)
	private String documento; 
	
	@Column(name = "nombre_empresa")
	private String nombreEmpresa;
	
	@Column(name = "primer_nombre", nullable = false)
	private String primerNombre;
	
	@Column(name = "segundo_nombre", nullable = true)
	private String segundoNombre;
	
	@Column(name = "primer_apellido", nullable = false)
	private String primerApellido;
	
	@Column(name = "segundo_apellido", nullable = true)
	private String segundoApellido;
	
	@Column(name = "correo", nullable = false, unique = true)
	private String correo;
	
	@Column(name = "password", nullable = false)
	private String password;
	
	@Column(name = "estaHabilitado")
	private boolean estaHabilitado;
	
	@Column(name = "nombre_completo", nullable = false)
	private String nombreCompleto;
	
	
	@PrePersist
	public void createFullName() {
		String segundoNombre = this.segundoNombre == null ? "" : this.segundoNombre;
		String segundoApellido = this.segundoApellido == null ? "" : this.segundoApellido;

		this.nombreCompleto = this.primerNombre.concat(" ").concat(segundoNombre).concat(" ").concat(this.primerApellido).concat(" ").concat(segundoApellido);
	}
	 
	
	@OneToMany(mappedBy = "usuario")
	private List<Token> tokens;
	 
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usuarios_roles", joinColumns = @JoinColumn(name = "id_usuario", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "id_rol", referencedColumnName = "id"))
	@JsonBackReference
	@Builder.Default
	private Set<Rol> roles = new HashSet<>();
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tipo_documento")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@JsonProperty(access = Access.WRITE_ONLY)
	private TipoDocumento tipoDocumento;
	
	
	@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	@Builder.Default
	private Set<Telefono> telefonos = new HashSet<>();
	
	
	@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	@Builder.Default
	private Set<Direccion> direcciones = new HashSet<>();
	

}

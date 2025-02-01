package com.softman.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
		value = {"id", "createdAt", "modifiedAt"},
		allowGetters = true)
public abstract class BaseEntityListener implements Serializable {

	private static final long serialVersionUID = 1L; 
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
		
	@Column(name = "created_at", nullable = false)
	protected Timestamp createdAt;
	
	@Column(name = "modified_at", nullable = true)
	protected Timestamp modifiedAt;
	
	@PrePersist
	public void prePersist(){
		Date date = new Date();
		createdAt = new Timestamp(date.getTime());		
	}
	
	@PreUpdate
	public void preUpdate() {
		Date date = new Date();
		this.modifiedAt = new Timestamp(date.getTime());
		setModifiedAt(this.modifiedAt);
	}
	
}

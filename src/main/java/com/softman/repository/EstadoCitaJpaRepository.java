package com.softman.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.softman.entity.EstadoCita;

public interface EstadoCitaJpaRepository extends JpaRepository<EstadoCita, Long> {

	EstadoCita findEstadoCitaByEstado(String nombreEstado) throws Exception;
}

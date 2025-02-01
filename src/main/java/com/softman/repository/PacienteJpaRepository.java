package com.softman.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.softman.entity.Paciente;

public interface PacienteJpaRepository extends JpaRepository<Paciente, Long>  {

}

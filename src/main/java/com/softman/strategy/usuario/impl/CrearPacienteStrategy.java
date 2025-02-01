package com.softman.strategy.usuario.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softman.dto.RegistroRequestDTO;
import com.softman.entity.Paciente;
import com.softman.entity.Usuario;
import com.softman.enumeration.RolEnum;
import com.softman.service.PacienteService;
import com.softman.strategy.usuario.CrearSubUsuarioStrategy;

import jakarta.annotation.PostConstruct;


@Service
@Qualifier("createSeller")
public class CrearPacienteStrategy implements CrearSubUsuarioStrategy {
	
	@Autowired
	private PacienteService pacienteService;
	
	
	@PostConstruct
	@Override
	public void registrar() {
		CrearSubUsuarioExecutorImpl.agregarCrearSubUsuarioStrategy(RolEnum.PACIENTE, this);	
	}
	

	@Transactional
	@Override
	public Long crearSubUsuario(Usuario usuario, RegistroRequestDTO registroRequestDTO) throws Exception {

		Paciente nuevoPaciente = Paciente.builder()
				.usuario(usuario)
				.build();
		
		nuevoPaciente = pacienteService.guardarPaciente(nuevoPaciente);
				
		return nuevoPaciente.getId();
	}

}

package com.softman.strategy.usuario.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softman.dto.RegistroRequestDTO;
import com.softman.entity.Medico;
import com.softman.entity.Usuario;
import com.softman.enumeration.RolEnum;
import com.softman.exception.BusinessException;
import com.softman.service.MedicoService;
import com.softman.strategy.usuario.CrearSubUsuarioStrategy;

import jakarta.annotation.PostConstruct;


@Service
@Qualifier("crearMedico")
public class CrearMedicoStrategy implements CrearSubUsuarioStrategy {
	
	@Autowired
	private MedicoService medicoService;
		
	
	@PostConstruct
	@Override
	public void registrar() {
		CrearSubUsuarioExecutorImpl.agregarCrearSubUsuarioStrategy(RolEnum.MEDICO, this);	
	}


	@Transactional
	@Override
	public Long crearSubUsuario(Usuario usuario, RegistroRequestDTO registroRequestDTO) throws Exception {
		
		if(registroRequestDTO.getRethus().isEmpty()) {
			throw new BusinessException("El parámetro rethus no debe ser vacío ni nulo si el rol es médico");
		}
		
		Medico nuevoMedico = Medico.builder()
				.rethus(registroRequestDTO.getRethus())
				.usuario(usuario)
				.build();
		
		nuevoMedico = medicoService.guardarMedico(nuevoMedico);
		
		return nuevoMedico.getId();
	}
	
}

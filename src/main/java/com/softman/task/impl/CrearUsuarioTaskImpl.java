package com.softman.task.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softman.dto.DireccionDTO;
import com.softman.dto.RegistroRequestDTO;
import com.softman.dto.RegistroResponseDTO;
import com.softman.dto.TelefonoDTO;
import com.softman.entity.Direccion;
import com.softman.entity.Rol;
import com.softman.entity.Telefono;
import com.softman.entity.TipoDocumento;
import com.softman.entity.Usuario;
import com.softman.enumeration.RolEnum;
import com.softman.exception.BusinessException;
import com.softman.mapper.DireccionMapper;
import com.softman.mapper.TelefonoMapper;
import com.softman.service.DireccionService;
import com.softman.service.RolService;
import com.softman.service.TelefonoService;
import com.softman.service.TipoDocumentoService;
import com.softman.service.UsuarioService;
import com.softman.strategy.usuario.CrearSubUsuarioExecutor;
import com.softman.task.CrearUsuarioTask;


@Service
public class CrearUsuarioTaskImpl implements CrearUsuarioTask {
	
	@Autowired
	private UsuarioService usuarioService;
		
	@Autowired
	private CrearSubUsuarioExecutor crearSubUsuarioExecutor;
	
	@Autowired
	private RolService rolService;
	
	@Autowired
	private TipoDocumentoService tipoDocumentoService;
		
	@Autowired
	private DireccionService direccionService;
	
	@Autowired
	private TelefonoService telefonoService;
	
	@Autowired
	private DireccionMapper direccionMapper;
	
	@Autowired
	private TelefonoMapper telefonoMapper;
	

	@Transactional
	@Override
	public RegistroResponseDTO crearUsuario(RegistroRequestDTO registroRequestDTO) throws Exception {
		
		Usuario usuarioCorreoEntityDB = usuarioService.buscarUsuarioPorCorreo(registroRequestDTO.getCorreo());
		
		if(usuarioCorreoEntityDB != null) {
			throw new BusinessException("El correo ingresado ya esta registrado");
		}
						
		Rol rolEntity = rolService.buscarRolPorId(registroRequestDTO.getIdRol());
		
		if(rolEntity == null) {
			throw new BusinessException("El idRol ingresado no existe");
		}
		
		String rolNombre = rolEntity.getNombre().split("_")[1];
		
		if(!rolNombre.equals(RolEnum.MEDICO.getValue()) && !rolNombre.equals(RolEnum.PACIENTE.getValue())) {
			throw new BusinessException("El idRol no es válido");
		}
		
		Set<Rol> rolesEntity = new HashSet<>();
		rolesEntity.add(rolEntity);
		
		TipoDocumento tipoDocumento = getTipoDocumento(registroRequestDTO);
			
		Usuario nuevoUsuario = Usuario.builder()
				.documento(registroRequestDTO.getDocumento())
				.nombreEmpresa(registroRequestDTO.getNombreEmpresa())
				.primerNombre(registroRequestDTO.getPrimerNombre())
		        .segundoNombre(registroRequestDTO.getSegundoNombre())
		        .primerApellido(registroRequestDTO.getPrimerApellido())
		        .segundoApellido(registroRequestDTO.getSegundoApellido())
				.correo(registroRequestDTO.getCorreo())
		        .password(registroRequestDTO.getPassword())   
		        .estaHabilitado(true)
		        .roles(rolesEntity)
		        .tipoDocumento(tipoDocumento)
		        .build();

		nuevoUsuario = usuarioService.guardarUsuario(nuevoUsuario);

		setDirecciones(registroRequestDTO, nuevoUsuario);
		
		setTelefonos(registroRequestDTO, nuevoUsuario);
		
		Long idSubUsuario = crearSubUsuarioExecutor.crearSubUsuario(RolEnum.valueOf(rolNombre), nuevoUsuario, registroRequestDTO);
		
		RegistroResponseDTO registerResponseDTO = setRegistroResponseDTO(registroRequestDTO, nuevoUsuario, idSubUsuario, rolNombre);

		return registerResponseDTO;
	}
	
	
	private TipoDocumento getTipoDocumento(RegistroRequestDTO registroRequestDTO) throws Exception {
		TipoDocumento tipoDocumentoTipoDocumento = tipoDocumentoService.buscarTipoDocumentoPorId(registroRequestDTO.getIdTipoDocumento());
		
		if(tipoDocumentoTipoDocumento == null) {
			throw new BusinessException("The entered guidTipoDocumento does not exist");
		}
		
		return tipoDocumentoTipoDocumento;
	}
	
	
	private void setDirecciones(RegistroRequestDTO registroRequestDTO, Usuario nuevoUsuario) throws Exception {
		List<Direccion> direcciones = direccionMapper.direccionDTOListToDireccionList(registroRequestDTO.getDirecciones());
		
		for (Direccion direccion : direcciones) {
			direccion.setUsuario(nuevoUsuario);
		}

		direcciones = direccionService.guardarDirecciones(direcciones);
		
		List<DireccionDTO> direccionesDTO = direccionMapper.direccionListToDireccionDTOList(direcciones);
		
		registroRequestDTO.setDirecciones(direccionesDTO);
	}

	
	private void setTelefonos(RegistroRequestDTO registroRequestDTO, Usuario nuevoUsuario) throws Exception {
		List<Telefono> telefonos = telefonoMapper.telefonoDTOListToTelefonoList(registroRequestDTO.getTelefonos());
		
		for (Telefono telefono : telefonos) {
			telefono.setUsuario(nuevoUsuario);
		}

		telefonos = telefonoService.guardarTelefonos(telefonos);
		
		List<TelefonoDTO> telefonosDTO = telefonoMapper.telefonoListToTelefonoDTOList(telefonos);
		
		registroRequestDTO.setTelefonos(telefonosDTO);
	}
	
	private RegistroResponseDTO setRegistroResponseDTO(RegistroRequestDTO registroRequestDTO, Usuario nuevoUsuario, Long idSubUsuario, String rolNombre) throws Exception {
		RegistroResponseDTO registroResponseDTO = new RegistroResponseDTO();
		
		BeanUtils.copyProperties(registroRequestDTO, registroResponseDTO);
		
		registroResponseDTO.setIdUsuario(nuevoUsuario.getId());		
		registroResponseDTO.setDirecciones(registroRequestDTO.getDirecciones());		
		registroResponseDTO.setTelefonos(registroRequestDTO.getTelefonos());
		
		if(rolNombre.equals(RolEnum.MEDICO.getValue())) {
			registroResponseDTO.setIdMedico(idSubUsuario);
		}
		
		if(rolNombre.equals(RolEnum.PACIENTE.getValue())) {
			registroResponseDTO.setIdPaciente(idSubUsuario);
		}
		
		return registroResponseDTO;
	}
	
}

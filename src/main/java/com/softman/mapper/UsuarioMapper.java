package com.softman.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.softman.dto.UsuarioDTO;
import com.softman.entity.Usuario;


@Mapper(componentModel = "spring",
uses = {
		DireccionMapper.class,
		TelefonoMapper.class
   }
)
public interface UsuarioMapper {

	// Usuario to UsuarioDTO
	
	UsuarioDTO usuarioToUsuarioDTO(Usuario usuario) throws Exception;

	List<UsuarioDTO> usuarioListToUsuarioDTOList(List<Usuario> usuarioList) throws Exception;
	
	
	// UsuarioDTO to Usuario

	Usuario usuarioDTOToUsuario(UsuarioDTO usuarioDTO) throws Exception;
	
	List<Usuario> usuarioDTOListToUsuarioList(List<UsuarioDTO> usuarioDTOList) throws Exception;
	
}

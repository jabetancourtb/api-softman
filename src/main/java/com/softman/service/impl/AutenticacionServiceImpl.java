package com.softman.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softman.configuration.security.MyUserDetails;
import com.softman.dto.AutenticacionRequestDTO;
import com.softman.dto.AutenticacionResponseDTO;
import com.softman.dto.RegistroRequestDTO;
import com.softman.dto.RegistroResponseDTO;
import com.softman.entity.Token;
import com.softman.entity.Usuario;
import com.softman.enumeration.TokenType;
import com.softman.jwt.JwtService;
import com.softman.service.AutenticacionService;
import com.softman.service.TokenService;
import com.softman.service.UsuarioService;
import com.softman.task.CrearUsuarioTask;

import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Service
public class AutenticacionServiceImpl implements AutenticacionService {
	
	private static final BCryptPasswordEncoder BCRYPT_ENCODER = new BCryptPasswordEncoder();
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UsuarioService usuarioService;
		
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private CrearUsuarioTask crearUsuarioTask;

	
	@Override
	@Transactional
	public RegistroResponseDTO registrarUsuario(RegistroRequestDTO registroRequestDTO) throws Exception {
		registroRequestDTO.setPassword(BCRYPT_ENCODER.encode(registroRequestDTO.getPassword()));
		
		RegistroResponseDTO registroResponseDTO = crearUsuarioTask.crearUsuario(registroRequestDTO);
		
		Usuario nuevoUsuario = usuarioService.buscarUsuarioPorCorreo(registroResponseDTO.getCorreo());
		
		String jwtToken = jwtService.generateToken(nuevoUsuario, new MyUserDetails(nuevoUsuario));
		String refreshToken = jwtService.generateRefreshToken(nuevoUsuario, new MyUserDetails(nuevoUsuario));
		guardarTokenUsuario(nuevoUsuario, jwtToken);
		
		AutenticacionResponseDTO authResponse = AutenticacionResponseDTO.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
		
		registroResponseDTO.setAutenticacionResponseDTO(authResponse);
		
		return registroResponseDTO;
	}

	
	@Override
	public AutenticacionResponseDTO login(AutenticacionRequestDTO autenticacionRequestDTO) throws Exception {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				autenticacionRequestDTO.getCorreo(),
				autenticacionRequestDTO.getPassword()
        ));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);

		final Usuario usuario = usuarioService.buscarUsuarioPorCorreo(autenticacionRequestDTO.getCorreo());

		String jwtToken = jwtService.generateToken(usuario, new MyUserDetails(usuario));
		
		String refreshToken = jwtService.generateRefreshToken(usuario, new MyUserDetails(usuario));
		
		revocarTodosLosTokensDeUsuario(usuario);
		
	    guardarTokenUsuario(usuario, jwtToken);
		
	    return AutenticacionResponseDTO.builder()
	            .accessToken(jwtToken)
	            .refreshToken(refreshToken)
	            .build();
	}

	
	@Override
	public AutenticacionResponseDTO refreshToken(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
	    final String refreshToken;
	    final String correoUsuario;
	    AutenticacionResponseDTO authResponse = null;
	    
	    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
	      throw new AuthException("El header authentication es nulo o vacío");
	    }
	    
	    refreshToken = authHeader.substring(7);
	    
	    correoUsuario = jwtService.getUsernameFromToken(refreshToken);
	    
	    if (correoUsuario == null) {
	    	throw new AuthException("El refresh token en el header authentication es incorrecto");
	    }
	  
	    Usuario usuario = this.usuarioService.buscarUsuarioPorCorreo(correoUsuario);
      
	    if (jwtService.validateToken(refreshToken, new MyUserDetails(usuario))) {
	    	var accessToken = jwtService.generateToken(usuario, new MyUserDetails(usuario));
        
	    	revocarTodosLosTokensDeUsuario(usuario);
	    	guardarTokenUsuario(usuario, accessToken);
        
	    	authResponse = AutenticacionResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
	    }
	    
	    return authResponse;
	}
	
	
	private void guardarTokenUsuario(Usuario usuario, String jwtToken) throws Exception {
	    var token = Token.builder()
	        .usuario(usuario)
	        .token(jwtToken)
	        .tokenType(TokenType.BEARER)
	        .expired(false)
	        .revoked(false)
	        .build();
	    
	    tokenService.guardarToken(token);
	}

	
	private void revocarTodosLosTokensDeUsuario(Usuario usuario) throws Exception {
	    var validUserTokens = tokenService.buscarTodosLosTokensValidosPorIdUsuario(usuario.getId());
	    
	    if (validUserTokens.isEmpty()) {
	    	return;
	    }
	    
	    validUserTokens.forEach(token -> {
	      token.setExpired(true);
	      token.setRevoked(true);
	    });
	    
	    tokenService.guardarTodos(validUserTokens);
	}

}

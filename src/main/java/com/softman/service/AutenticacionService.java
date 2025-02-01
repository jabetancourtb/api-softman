package com.softman.service;

import com.softman.dto.AutenticacionRequestDTO;
import com.softman.dto.AutenticacionResponseDTO;
import com.softman.dto.RegistroRequestDTO;
import com.softman.dto.RegistroResponseDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AutenticacionService {

	RegistroResponseDTO registrarUsuario(RegistroRequestDTO registroRequestDTO) throws Exception;

	AutenticacionResponseDTO login(AutenticacionRequestDTO autenticacionRequestDTO) throws Exception;
	
	AutenticacionResponseDTO refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
}

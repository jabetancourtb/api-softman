package com.softman.service;

import java.util.List;
import java.util.Optional;

import com.softman.entity.Token;


public interface TokenService {

	Token guardarToken(Token token) throws Exception;
	
	List<Token> guardarTodos(List<Token> tokens) throws Exception;

	List<Token> buscarTodosLosTokensValidosPorIdUsuario(Long idUsuario);
	
	Optional<Token> buscarPorToken(String token);
	
}

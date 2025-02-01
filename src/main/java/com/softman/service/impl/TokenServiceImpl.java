package com.softman.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softman.entity.Token;
import com.softman.repository.TokenJpaRepository;
import com.softman.service.TokenService;

@Service
public class TokenServiceImpl implements TokenService {
	
	@Autowired
	private TokenJpaRepository tokenJpaRepository;
	
	
	@Override
	public Token guardarToken(Token token) throws Exception {
		return tokenJpaRepository.save(token);
	}
	
	
	@Override
	public List<Token> guardarTodos(List<Token> tokens) throws Exception {
		return tokenJpaRepository.saveAll(tokens);
	}

	
	@Override
	public List<Token> buscarTodosLosTokensValidosPorIdUsuario(Long idUser) {
		return tokenJpaRepository.buscarTodosLosTokensValidosPorIdUsuario(idUser);
	}

	
	@Override
	public Optional<Token> buscarPorToken(String token) {
		return tokenJpaRepository.findByToken(token);
	}
}

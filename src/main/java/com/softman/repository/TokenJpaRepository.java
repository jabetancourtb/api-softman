package com.softman.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.softman.entity.Token;

public interface TokenJpaRepository extends JpaRepository<Token, Long> {

	@Query(value = """
			SELECT t.* FROM TOKENS t\s
			INNER JOIN USUARIOS u  ON t.id_usuario = u.id\s
		    WHERE u.id = :idUsuario AND (t.expired = false OR t.revoked = false)\s
		    """, nativeQuery = true)
	List<Token> buscarTodosLosTokensValidosPorIdUsuario(@Param("idUsuario") Long guidUser);
	
	Optional<Token> findByToken(String token);
	
}

package com.softman.configuration.security;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import com.softman.exception.LogoutException;
import com.softman.service.TokenService;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {
	
	private final TokenService tokenService;

 
    @Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
    	
    	try {
    		
    		final String authHeader = request.getHeader("Authorization");
    		
    		final String jwt;
    		    
		    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
		    	throw new LogoutException("El header authentication esta nulo");
		    }
		    
		    jwt = authHeader.substring(7);
		    
		    SecurityContextHolder.clearContext();
		    
		    var storedToken = tokenService.buscarPorToken(jwt).orElse(null);
		    
		    if (storedToken != null) {
		      storedToken.setExpired(true);
		      storedToken.setRevoked(true);
		      tokenService.guardarToken(storedToken);
		      SecurityContextHolder.clearContext();
		    }
    	}
    	catch(Exception exception) {
    		throw new LogoutException(exception.getMessage());
    	}    
    }
  
  
}

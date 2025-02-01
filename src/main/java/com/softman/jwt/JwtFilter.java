package com.softman.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.softman.configuration.security.CustomUserDetailsService;
import com.softman.service.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtUtil;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    
    @Autowired
    private TokenService tokenService;
    

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
    	
    	String authHeader = httpServletRequest.getHeader("Authorization");
        String token = null;
        String email = null;
    	
    	if (httpServletRequest.getServletPath().contains("/api/v1/auth")) {
	        filterChain.doFilter(httpServletRequest, httpServletResponse);
	        return;
	    }
    	
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        	filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        
        token = authHeader.substring(7);
        email = jwtUtil.getUsernameFromToken(token);
   
        if(email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        	
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
            
            var isTokenValid = tokenService.buscarPorToken(token)
                    .map(t -> !t.isExpired() && !t.isRevoked())
                    .orElse(false);
            
            if(jwtUtil.validateToken(token, userDetails) && isTokenValid) {
            	
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
                		new UsernamePasswordAuthenticationToken(
                				userDetails, 
                				null, 
                				userDetails.getAuthorities()
                		);

                usernamePasswordAuthenticationToken.setDetails(
                		new WebAuthenticationDetailsSource().buildDetails(httpServletRequest)
                );

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
package com.softman.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import com.softman.constant.URIConstants;
import com.softman.enumeration.RolEnum;
import com.softman.jwt.JwtFilter;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.RequiredArgsConstructor;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
	
	@Autowired
	private JwtFilter jwtFilter;
	
	@Autowired
	private LogoutHandler logoutHandler;
	
	private final AuthenticationProvider authenticationProvider;
	

	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http
	    .csrf(csrf -> csrf.disable())
	    .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable())) 
    	.authorizeHttpRequests(requests -> requests
			.requestMatchers(WHITE_LIST_URL)
			.permitAll()
			
			.requestMatchers(GET, URIConstants.CENTROS_MEDICOS+"/**").hasAnyRole(RolEnum.ADMIN.getValue(), RolEnum.MEDICO.getValue(), RolEnum.PACIENTE.getValue())
			.requestMatchers(POST, URIConstants.CENTROS_MEDICOS+"/**").hasAnyRole(RolEnum.ADMIN.getValue())
			.requestMatchers(PUT, URIConstants.CENTROS_MEDICOS+"/**").hasAnyRole(RolEnum.ADMIN.getValue())
			.requestMatchers(DELETE, URIConstants.CENTROS_MEDICOS+"/**").hasAnyRole(RolEnum.ADMIN.getValue())
			
			.requestMatchers(URIConstants.CITAS+"/**").hasAnyRole(RolEnum.ADMIN.getValue(), RolEnum.MEDICO.getValue(), RolEnum.PACIENTE.getValue())
			
			.requestMatchers(URIConstants.DIRECCIONES+"/**").hasAnyRole(RolEnum.ADMIN.getValue(), RolEnum.MEDICO.getValue(), RolEnum.PACIENTE.getValue())
			
			.requestMatchers(URIConstants.DIRECCIONES+"/**").hasAnyRole(RolEnum.ADMIN.getValue(), RolEnum.MEDICO.getValue(), RolEnum.PACIENTE.getValue())
			
			.requestMatchers(URIConstants.ESPECIALIDADES+"/**").hasAnyRole(RolEnum.ADMIN.getValue(), RolEnum.MEDICO.getValue(), RolEnum.PACIENTE.getValue())
			
			.requestMatchers(URIConstants.ESTADOS_CITAS+"/**").hasAnyRole(RolEnum.ADMIN.getValue(), RolEnum.MEDICO.getValue(), RolEnum.PACIENTE.getValue())
			
			.requestMatchers(URIConstants.MEDICOS+"/**").hasAnyRole(RolEnum.ADMIN.getValue(), RolEnum.MEDICO.getValue(), RolEnum.PACIENTE.getValue())
			
			.requestMatchers(URIConstants.PACIENTES+"/**").hasAnyRole(RolEnum.ADMIN.getValue(), RolEnum.MEDICO.getValue(), RolEnum.PACIENTE.getValue())
			
			.requestMatchers(URIConstants.PERMISOS+"/**").hasAnyRole(RolEnum.ADMIN.getValue(), RolEnum.MEDICO.getValue(), RolEnum.PACIENTE.getValue())
			
			.requestMatchers(URIConstants.ROLES+"/**").hasAnyRole(RolEnum.ADMIN.getValue(), RolEnum.MEDICO.getValue(), RolEnum.PACIENTE.getValue())
			
			.requestMatchers(URIConstants.TELEFONOS+"/**").hasAnyRole(RolEnum.ADMIN.getValue(), RolEnum.MEDICO.getValue(), RolEnum.PACIENTE.getValue())
			
			.requestMatchers(URIConstants.TIPOS_DOCUMENTOS+"/**").hasAnyRole(RolEnum.ADMIN.getValue(), RolEnum.MEDICO.getValue(), RolEnum.PACIENTE.getValue())
			
			.requestMatchers(URIConstants.USUARIOS+"/**").hasAnyRole(RolEnum.ADMIN.getValue(), RolEnum.MEDICO.getValue(), RolEnum.PACIENTE.getValue())
			
			//.requestMatchers(GET, "/api/v1/**").hasAnyAuthority("Admin_Read", "Admin_Create")
			.anyRequest()
			.authenticated()
		)
    	.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    	 .authenticationProvider(authenticationProvider)
         .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
         .logout(logout ->
                 logout.logoutUrl("/auth/logout")
                         .addLogoutHandler(logoutHandler)
                         .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
         );

	    return http.build();
	}
	
	
	private static final String[] WHITE_LIST_URL = {
			"/swagger-ui.html/**", 
			"/swagger-ui/**",
			"/swagger-resources",
			"/swagger-resources/**",
			"/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
			"/api-docs/**",
			"/css/**",
			"/fonts/**",
			"/img/**",
			"/js/**",
			"/auth/**",
			"/h2/**",
			URIConstants.USUARIOS+"/{isUsuario}"+URIConstants.MEDICOS,
			URIConstants.USUARIOS+"/{isUsuario}"+URIConstants.PACIENTES,		
	};
	
}

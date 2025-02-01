package com.softman.configuration.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.softman.entity.Rol;
import com.softman.entity.Usuario;


public class MyUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	private String username;
    private String password;
    private List<GrantedAuthority> authorities;
	

	public MyUserDetails(Usuario usuario) {
		this.username = usuario.getCorreo();
        this.password = usuario.getPassword();
        
        List<GrantedAuthority> permissions = new ArrayList<>();
        
        for (Rol rol : usuario.getRoles()) {
        	for(SimpleGrantedAuthority authority : rol.getAuthorities()) {
        		permissions.add(new SimpleGrantedAuthority(authority.toString()));
        	} 
        }
        
        this.authorities = permissions;
	}

	
	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

	
	@Override
	public String getPassword() {
		return password;
	}

	
	@Override
	public String getUsername() {
		return username;
	}

	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	
	@Override
	public boolean isEnabled() {
		return true;
	}
	

}

package com.softman.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.softman.entity.Usuario;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;


    // Retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    
    // Retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    
    // Retrieve specific claim from jwt token
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }


    // Retrieving any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts
        		.parserBuilder()
        		.setSigningKey(getSignInKey())
        		.build()
        		.parseClaimsJws(token)
        		.getBody();
    }


    // Check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
    
    
    // Generate all claims for user
    public Map<String, Object> setClaims(Usuario usuario, UserDetails userDetails) {
    	 Map<String, Object> claims = new HashMap<>();
         claims.put("id", usuario.getId());
         claims.put("fullname", usuario.getNombreCompleto());
         claims.put("enabled", usuario.isEstaHabilitado());
         claims.put("authorities", userDetails.getAuthorities());
         //claims.put("verificationCode", user.getVerificationCode());
         return claims;
    }
    
    
    // Generate refresh token for user
    public String generateRefreshToken(Usuario usuario, UserDetails userDetails) {
    	Map<String, Object> claims = setClaims(usuario, userDetails);
        return doGenerateToken(claims, userDetails.getUsername(), refreshExpiration);
    }


    // Generate token for user
    public String generateToken(Usuario usuario, UserDetails userDetails) {
    	Map<String, Object> claims = setClaims(usuario, userDetails);  
        return doGenerateToken(claims, userDetails.getUsername(), jwtExpiration);
    }


    // While creating the token -
    // 1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
    // 2. Sign the JWT using the HS512 algorithm and secret key.
    private String doGenerateToken(Map<String, Object> claims, String subject, long expiration) {
    	return Jwts
    			.builder()
    			.setClaims(claims)
    			.setSubject(subject)
    			.setIssuedAt(new Date(System.currentTimeMillis()))
    			.setExpiration(new Date(System.currentTimeMillis() + expiration))
    			.signWith(getSignInKey(), SignatureAlgorithm.HS256)
    			.compact();  
    }


    // Validate token
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    
    
    private Key getSignInKey() {
	    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
	    return Keys.hmacShaKeyFor(keyBytes);
	}
    
}
package com.ecommerce.config;

import com.ecommerce.properties.JwtProperties;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtTokenHelper {

    private final JwtProperties jwtProperties;

    public String getUsernameFromToken(String token) throws AuthenticationException {
        String claimFromToken = null;
        boolean invalidToken = false;
        try {
            claimFromToken = getClaimFromToken(
                    token,
                    Claims::getSubject
            );
        } catch (IllegalArgumentException e) {
            log.warn("Unable to get JWT Token, token = {}", token);
            invalidToken = true;
        } catch (ExpiredJwtException e) {
            log.warn("JWT Token has expired, token = {}", token);
            invalidToken = true;
        } catch (MalformedJwtException e) {
            log.warn("JWT Token is invalid, token = {}", token);
            invalidToken = true;
        }
        if (invalidToken) {
            throw new BadCredentialsException("JWT token is expired or invalid");
        }


        return claimFromToken;
    }


    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(
                token,
                Claims::getExpiration
        );
    }

    public <T> T getClaimFromToken(
            String token,
            Function<Claims, T> claimsResolver
    ) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecret())
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Boolean ignoreTokenExpiration() {
        return false;
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(
                claims,
                userDetails.getUsername()
        );
    }

    private String doGenerateToken(
            Map<String, Object> claims,
            String subject
    ) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getValidity())).signWith(
                        SignatureAlgorithm.HS512,
                        jwtProperties.getSecret()
                ).compact();
    }

    public Boolean canTokenBeRefreshed(String token) {
        return (!isTokenExpired(token) || ignoreTokenExpiration());
    }

    public Boolean validateToken(String token, UserDetails userDetails) throws AuthenticationException {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}

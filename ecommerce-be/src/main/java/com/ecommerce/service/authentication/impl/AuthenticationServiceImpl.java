package com.ecommerce.service.authentication.impl;

import com.ecommerce.config.JwtTokenHelper;
import com.ecommerce.service.authentication.AuthenticationService;
import com.ecommerce.service.authentication.model.AuthenticationRequest;
import com.ecommerce.service.authentication.model.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtTokenHelper jwtTokenHelper;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) throws AuthenticationException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));

        final var userDetails = userDetailsService
                .loadUserByUsername(request.getEmail());
        final String token = jwtTokenHelper.generateToken(userDetails);
        var expirationDate = jwtTokenHelper.getExpirationDateFromToken(token);

        return AuthenticationResponse.builder()
                .token(token)
                .expiresIn(expirationDate.getTime())
                .build();
    }
}

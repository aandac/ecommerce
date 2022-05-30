package com.ecommerce.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


@Component
@RequiredArgsConstructor
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtTokenHelper jwtTokenHelper;
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain chain
    ) {
        try {
            final String requestTokenHeader = request.getHeader("Authorization");
            String username = null;
            String password = null;
            String jwtToken = null;
            if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
                jwtToken = requestTokenHeader.substring(7);
                username = jwtTokenHelper.getUsernameFromToken(jwtToken);
            }
            if (requestTokenHeader != null && requestTokenHeader.startsWith("Basic")) {
                var base64Credentials = requestTokenHeader.substring(6);
                var credDecoded = Base64.getDecoder().decode(base64Credentials);
                var credentials = new String(credDecoded, StandardCharsets.UTF_8);
                var values = credentials.split(":", 2);
                if (values.length == 2) {
                    username = values[0];
                    password = values[1];
                }
                if (username != null) {
                    try {
                        var userDetails = this.userDetailsService.loadUserByUsername(username);
                        var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                password,
                                userDetails.getAuthorities()
                        );
                        usernamePasswordAuthenticationToken
                                .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    } catch (UsernameNotFoundException e) {
                        log.warn("Username not found {}", username);
                        SecurityContextHolder.getContext().setAuthentication(null);
                    }
                }
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                try {
                    var userDetails = this.userDetailsService.loadUserByUsername(username);
                    if (jwtTokenHelper.validateToken(
                            jwtToken,
                            userDetails
                    )) {
                        var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                password,
                                userDetails.getAuthorities()
                        );
                        usernamePasswordAuthenticationToken
                                .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }
                } catch (UsernameNotFoundException e) {
                    log.warn("Username not found {}", username);
                    SecurityContextHolder.getContext().setAuthentication(null);
                }

            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            // handle with global resolver
            handlerExceptionResolver.resolveException(request, response, null, e);
        }

    }

}

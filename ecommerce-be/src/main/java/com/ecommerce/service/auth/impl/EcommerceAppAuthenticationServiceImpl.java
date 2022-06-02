package com.ecommerce.service.auth.impl;

import com.ecommerce.dao.entity.User;
import com.ecommerce.dao.repo.UserRepository;
import com.ecommerce.service.auth.EcommerceAppAuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class EcommerceAppAuthenticationServiceImpl implements EcommerceAppAuthenticationService {

    private final UserRepository userRepository;
    @Override
    public Optional<User> getAuthenticatedUserWithRoles() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(authentication) ) {
            return Optional.empty();
        }

        var currentUserName = authentication.getName();
        return userRepository.findByEmailWithRoles(currentUserName);
    }
}

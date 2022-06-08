package com.ecommerce.service.auth;

import com.ecommerce.dao.entity.User;

import java.util.Optional;

public interface EcommerceAppAuthenticationService {

    Optional<User> getAuthenticatedUserWithRoles();

    Optional<String> getAuthenticatedUserName();
}

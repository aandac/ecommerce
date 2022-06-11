package com.ecommerce.service.users;

import com.ecommerce.dao.entity.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findByEmail(String email);

    Optional<User> getUser();
}

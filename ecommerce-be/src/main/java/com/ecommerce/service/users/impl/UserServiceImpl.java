package com.ecommerce.service.users.impl;

import com.ecommerce.dao.entity.User;
import com.ecommerce.dao.repo.UserRepository;
import com.ecommerce.service.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmailWithRoles(email);
    }
}

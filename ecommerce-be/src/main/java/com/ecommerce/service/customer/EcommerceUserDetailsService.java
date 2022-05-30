package com.ecommerce.service.customer;

import com.ecommerce.dao.entity.UserRole;
import com.ecommerce.service.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EcommerceUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userService.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Users not found with email: " + username
                ));

        return User.builder()
                .username(username)
                .authorities(user.getUserRoles().stream()
                        .map(userRole -> new SimpleGrantedAuthority(
                                userRole.getRole().name()))
                        .collect(Collectors.toList()))
                .roles(user.getUserRoles().stream()
                        .map(UserRole::getRole)
                        .map(Enum::name)
                        .toArray(String[]::new))
                .password(user.getPassword())
                .build();
    }

}

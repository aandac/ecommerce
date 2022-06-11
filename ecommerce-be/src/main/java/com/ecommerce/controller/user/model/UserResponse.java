package com.ecommerce.controller.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 8856116481047985849L;
    private Long userId;
    private String email;
    private Set<String> roles;
}

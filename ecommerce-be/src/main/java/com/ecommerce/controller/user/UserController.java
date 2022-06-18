package com.ecommerce.controller.user;

import com.ecommerce.controller.model.BaseResponse;
import com.ecommerce.controller.user.model.UserResponse;
import com.ecommerce.dao.entity.UserRole;
import com.ecommerce.service.customer.CustomerService;
import com.ecommerce.service.users.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final CustomerService customerService;

    @GetMapping
    @Operation(tags = "user",
            description = "Returns the logged-in user",
            security = {@SecurityRequirement(name = "bearer-key")},
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Returns the logged-in user",
                            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserResponse.class))})}
    )
    public BaseResponse getUser() {
        var user = userService.getUser()
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "User not found"));
        return BaseResponse.builder()
                .payload(UserResponse.builder()
                        .userId(user.getId())
                        .email(user.getEmail())
                        .roles(user.getUserRoles()
                                .stream()
                                .map(UserRole::getRole)
                                .map(Enum::name)
                                .collect(Collectors.toSet()))
                        .build())
                .build();
    }


}

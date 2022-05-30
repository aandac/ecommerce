package com.ecommerce.controller.authentication;

import com.ecommerce.controller.model.BaseResponse;
import com.ecommerce.service.authentication.AuthenticationService;
import com.ecommerce.service.authentication.model.AuthenticationRequest;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/public/authentication")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/email")
    @Operation(tags = "authentication")
    public BaseResponse authenticateWithEmail(@Valid @RequestBody AuthenticationRequest authRequest)
            throws AuthenticationException {
        return BaseResponse.builder()
                .payload(authenticationService.authenticate(authRequest))
                .build();
    }

}

package com.ecommerce.service.authentication;

import com.ecommerce.service.authentication.model.AuthenticationRequest;
import com.ecommerce.service.authentication.model.AuthenticationResponse;

public interface AuthenticationService {

    AuthenticationResponse authenticate(AuthenticationRequest request);
}

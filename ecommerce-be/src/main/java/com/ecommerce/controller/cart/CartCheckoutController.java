
package com.ecommerce.controller.cart;

import com.ecommerce.controller.model.BaseResponse;
import com.ecommerce.dao.Roles;
import com.ecommerce.service.auth.EcommerceAppAuthenticationService;
import com.ecommerce.service.cart.CartService;
import com.ecommerce.service.cart.CheckoutService;
import com.ecommerce.service.cart.model.CartItemResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;

@Slf4j
@RestController
@RequestMapping("/api/cart/checkout")
@RequiredArgsConstructor
public class CartCheckoutController {

    private final CheckoutService checkoutService;
    private final EcommerceAppAuthenticationService authenticationService;


    @PostMapping
    @Operation(
            tags = "cart",
            description = "Checkout the customer cart",
            security = {@SecurityRequirement(name = "bearer-key")}
    )
    public BaseResponse checkoutCart() {
        var user = authenticationService.getAuthenticatedUserWithRoles()
                .orElseThrow(() -> new HttpClientErrorException(
                        HttpStatus.UNAUTHORIZED,
                        "User not found"
                ));

        var cartAmount = checkoutService.oneClickCheckout(user);
        return BaseResponse.builder()
                .payload(cartAmount)
                .build();
    }


}

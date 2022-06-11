package com.ecommerce.controller.cart;

import com.ecommerce.controller.model.BaseResponse;
import com.ecommerce.service.auth.EcommerceAppAuthenticationService;
import com.ecommerce.service.cart.CartService;
import com.ecommerce.service.cart.model.CartItemResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final EcommerceAppAuthenticationService authenticationService;


    @GetMapping
    @Operation(
            tags = "cart",
            description = "Gets the customer cart items",
            security = {@SecurityRequirement(name = "bearer-key")}
    )
    public BaseResponse getCart() {
        var userName = authenticationService.getAuthenticatedUserName()
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Authenticated user not provided"));

        var cartItems = cartService.getCartItems(userName);
        return BaseResponse.builder()
                .payload(cartItems)
                .build();
    }

    @PostMapping
    @Operation(
            tags = "cart",
            description = "Adds cart items to the list",
            security = {@SecurityRequirement(name = "bearer-key")}
    )
    public BaseResponse addToCart(@RequestBody CartItemResponse cartItem) {
        var userName = authenticationService.getAuthenticatedUserName()
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Authenticated user not provided"));

        var cartItemList = cartService.addCartItem(userName, cartItem);
        return BaseResponse.builder()
                .payload(cartItemList)
                .build();
    }

    @PostMapping("delete")
    @Operation(
            tags = "cart",
            description = "Deletes cart items from the list",
            security = {@SecurityRequirement(name = "bearer-key")}
    )
    public BaseResponse deleteFromCart(@RequestParam(value = "productId", required = false) String productId) {
        var userName = authenticationService.getAuthenticatedUserName()
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Authenticated user not provided"));

        var responseList = cartService.deleteCartItem(userName, productId);
        return BaseResponse.builder()
                .payload(responseList)
                .build();
    }
}

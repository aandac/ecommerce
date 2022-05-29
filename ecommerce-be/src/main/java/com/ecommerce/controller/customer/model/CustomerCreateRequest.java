package com.ecommerce.controller.customer.model;

import javax.validation.constraints.NotNull;

public record CustomerCreateRequest(
        @NotNull
        String email,
        @NotNull
        String password,
        String billingAddress,
        String shippingAddress,
        String creditCardNumber,
        Short creditCardExpireMonth,
        Short creditCardExpireYear,
        Short creditCardCvv) {
}

package com.ecommerce.controller.customer.model;

public record CustomerDetailUpdateRequest(
        String billingAddress,
        String shippingAddress,
        String creditCardNumber,
        Short creditCardExpireMonth,
        Short creditCardExpireYear,
        Short creditCardCvv) {
}

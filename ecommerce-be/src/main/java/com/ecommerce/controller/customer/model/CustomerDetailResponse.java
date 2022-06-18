package com.ecommerce.controller.customer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDetailResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 8856116481047985849L;
    private Long userId;
    private String billingAddress;
    private String shippingAddress;
    private String creditCardNumber;
    private Short creditCardExpireMonth;
    private Short creditCardExpireYear;
    private Short creditCardCvv;
}

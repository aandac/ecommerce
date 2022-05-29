package com.ecommerce.controller;

import com.ecommerce.controller.customer.model.CustomerCreateRequest;
import com.ecommerce.controller.model.BaseResponse;
import com.ecommerce.service.customer.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/public/customer")
@RequiredArgsConstructor
public class CustomerCreateController {

    private final CustomerService customerService;

    @PostMapping
    @Operation(tags = "customer", description = "Creates a customer with given parameters")
    public BaseResponse createCustomer(@Valid @RequestBody CustomerCreateRequest request) {
        customerService.createCustomer(request);
        return BaseResponse.builder()
                .build();
    }
}

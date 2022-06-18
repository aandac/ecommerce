package com.ecommerce.controller.customer;

import com.ecommerce.controller.customer.model.CustomerDetailResponse;
import com.ecommerce.controller.customer.model.CustomerDetailUpdateRequest;
import com.ecommerce.controller.model.BaseResponse;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/api/customer/detail")
@RequiredArgsConstructor
public class CustomerDetailController {

    private final CustomerService customerService;
    private final UserService userService;

    @GetMapping
    @Operation(tags = "customer",
            description = "Returns the customer detail info",
            security = {@SecurityRequirement(name = "bearer-key")},
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Returns the customer detail info",
                            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CustomerDetailResponse.class))})}
    )
    public BaseResponse getUserDetails() {
        var user = userService.getUser()
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "User not found"));
        var customerDetail = customerService.getCustomerDetail(user);
        return BaseResponse.builder()
                .payload(customerDetail)
                .build();
    }

    @PostMapping
    @Operation(tags = "customer",
            description = "Returns the customer detail info",
            security = {@SecurityRequirement(name = "bearer-key")},
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Returns the customer detail info",
                            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CustomerDetailResponse.class))})}
    )
    public BaseResponse updateCustomerDetail(@RequestBody CustomerDetailUpdateRequest request) {
        var user = userService.getUser()
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "User not found"));
        customerService.updateCustomerDetail(user, request);
        return BaseResponse.builder()
                .build();
    }
}

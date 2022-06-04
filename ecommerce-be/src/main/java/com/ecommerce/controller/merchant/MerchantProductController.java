package com.ecommerce.controller.merchant;


import com.ecommerce.controller.merchant.model.MerchantProductCreateRequest;
import com.ecommerce.dao.Roles;
import com.ecommerce.service.auth.EcommerceAppAuthenticationService;
import com.ecommerce.service.merchant.MerchantCreateProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MerchantProductController {

    private final EcommerceAppAuthenticationService authenticationService;
    private final MerchantCreateProductService merchantCreateProductService;

    @RequestMapping(
            path = "/api/merchant/product",
            method = RequestMethod.POST,
            consumes = {
                    MediaType.MULTIPART_FORM_DATA_VALUE
            })
    @Operation(tags = "merchant", description = "Creates a product")
    public ResponseEntity<Object> createProduct(
            @Valid @RequestPart("body") MerchantProductCreateRequest request,
            @RequestPart(value = "file", required = false) List<MultipartFile> documents
    ) throws HttpClientErrorException {

        var user = authenticationService.getAuthenticatedUserWithRoles()
                .filter(u -> u.getUserRoles().stream().anyMatch(a -> a.getRole().name().equals(Roles.MERCHANT.name())))
                .orElseThrow(() -> new HttpClientErrorException(
                        HttpStatus.UNAUTHORIZED,
                        "User not found"
                ));

        log.info("data {}", request);
        log.info("file size {}", documents.size());
        merchantCreateProductService.createProduct(user, request, documents);

        return ResponseEntity.ok().body("hello");
    }
}
package com.ecommerce.controller.merchant;


import com.ecommerce.controller.merchant.model.MerchantProductCreateRequest;
import com.ecommerce.dao.Roles;
import com.ecommerce.service.auth.EcommerceAppAuthenticationService;
import com.ecommerce.service.merchant.MerchantCreateProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MerchantProductController {

    private final EcommerceAppAuthenticationService authenticationService;
    private final MerchantCreateProductService merchantCreateProductService;

    @PostMapping("/api/merchant/product")
    @Operation(
            tags = "merchant",
            description = "Creates a product",
            security = {@SecurityRequirement(name = "bearer-key")}
    )
    public ResponseEntity<Object> createProduct(
            @RequestBody
            MerchantProductCreateRequest body
    ) throws HttpClientErrorException {
        var user = authenticationService.getAuthenticatedUserWithRoles()
                .filter(u -> u.getUserRoles().stream().anyMatch(a -> a.getRole().name().equals(Roles.MERCHANT.name())))
                .orElseThrow(() -> new HttpClientErrorException(
                        HttpStatus.UNAUTHORIZED,
                        "User not found"
                ));

        log.info("data {}", body);
        var product = merchantCreateProductService.createProduct(user, body);

        return ResponseEntity.ok().body(product);
    }

    @RequestMapping(
            path = "/api/merchant/product/file",
            method = RequestMethod.POST,
            consumes = {
                    MediaType.MULTIPART_FORM_DATA_VALUE
            })
    @Operation(
            tags = "merchant",
            description = "Creates a product",
            security = {@SecurityRequirement(name = "bearer-key")}
    )
    public ResponseEntity<Object> uploadProduct(
            @RequestPart("file") MultipartFile file
    ) throws HttpClientErrorException {

        var user = authenticationService.getAuthenticatedUserWithRoles()
                .filter(u -> u.getUserRoles().stream().anyMatch(a -> a.getRole().name().equals(Roles.MERCHANT.name())))
                .orElseThrow(() -> new HttpClientErrorException(
                        HttpStatus.UNAUTHORIZED,
                        "User not found"
                ));

        log.info("file size {}", file.getName());
        var uploadedFile = merchantCreateProductService.uploadFile(user, file);
        return ResponseEntity.ok().body(uploadedFile);
    }
}

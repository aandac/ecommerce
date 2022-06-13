package com.ecommerce.controller.merchant;


import com.ecommerce.controller.merchant.model.MerchantProductCreateRequest;
import com.ecommerce.dao.Roles;
import com.ecommerce.service.auth.EcommerceAppAuthenticationService;
import com.ecommerce.service.merchant.MerchantCreateProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import liquibase.repackaged.org.apache.commons.collections4.CollectionUtils;
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
    @Operation(
            tags = "merchant",
            description = "Creates a product",
            security = {@SecurityRequirement(name = "bearer-key")}
    )
    public ResponseEntity<Object> createProduct(
            @Valid @RequestPart("body")
            @Parameter(schema = @Schema(type = "string", format = "binary"))
            MerchantProductCreateRequest body,
            @RequestPart(value = "file", required = false) List<MultipartFile> documents
    ) throws HttpClientErrorException {

        var user = authenticationService.getAuthenticatedUserWithRoles()
                .filter(u -> u.getUserRoles().stream().anyMatch(a -> a.getRole().name().equals(Roles.MERCHANT.name())))
                .orElseThrow(() -> new HttpClientErrorException(
                        HttpStatus.UNAUTHORIZED,
                        "User not found"
                ));

        log.info("data {}", body);
        log.info("file size {}", CollectionUtils.isEmpty(documents) ? 0 : documents.size());
        merchantCreateProductService.createProduct(user, body, documents);

        return ResponseEntity.ok().body("hello");
    }
}

package com.ecommerce.controller.product;

import com.ecommerce.controller.model.BaseResponse;
import com.ecommerce.service.product.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/public/product")
@RequiredArgsConstructor
public class PublicProductController {

    private final ProductService productService;

    @GetMapping
    @Operation(
            tags = "products",
            description = "List products for simple way"
    )
    public BaseResponse getProducts(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        var products = productService.getProducts(page, size);
        return BaseResponse.builder()
                .payload(products)
                .build();
    }
}

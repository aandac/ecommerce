package com.ecommerce.service.product.model;

import java.math.BigDecimal;
import java.util.List;

public record ProductSimpleResponse(
        Long id,
        String title,
        List<String> images,
        BigDecimal price
) {
}

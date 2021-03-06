package com.ecommerce.controller.merchant.model;

import com.ecommerce.service.merchant.model.ShipmentDeliveryTimes;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public record MerchantProductCreateRequest(
        @NotNull
        String title,
        @NotNull
        String sku,
        @NotNull
        BigDecimal price,
        @NotNull
        Long inventory,
        @NotNull
        ShipmentDeliveryTimes shipmentDeliveryTimes,
        @NotNull
        Boolean active,
        List<String> files

) {
}

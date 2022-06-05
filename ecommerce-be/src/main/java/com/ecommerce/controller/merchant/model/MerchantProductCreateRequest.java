package com.ecommerce.controller.merchant.model;

import com.ecommerce.service.merchant.model.ShipmentDeliveryTimes;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public record MerchantProductCreateRequest(
        @NotNull
        String sku,
        @NotNull
        BigDecimal price,
        @NotNull
        Long inventory,
        @NotNull
        ShipmentDeliveryTimes shipmentDeliveryTimes,
        @NotNull
        Boolean active
) {
}

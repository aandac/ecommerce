package com.ecommerce.controller.merchant.model;

import java.math.BigDecimal;

public record MerchantProductCreateRequest(
        String sku,
        BigDecimal price,
        Integer inventory,
        ShipmentDeliveryTimes shipmentDeliveryTimes,
        Boolean active
) {
}

package com.ecommerce.service.faker;

import com.ecommerce.dao.entity.Product;
import com.ecommerce.service.merchant.model.ShipmentDeliveryTimes;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

@Slf4j
public class FakerTester {

    @Test
    void it_should_generate_productNames() throws JsonProcessingException {

        Faker faker = new Faker();
        var product = Product.builder()
                .title(faker.commerce().productName())
                .sku(faker.code().ean8())
                .inventory(faker.number().randomNumber(2,true))
                .price(BigDecimal.valueOf(Double.parseDouble(faker.commerce().price())))
                .shipmentDeliveryTimes(ShipmentDeliveryTimes.THREE_BUSINESS_DAY)
                .active(true)
                .build();

        var objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        log.info("Generated product {}", objectMapper.writeValueAsString(product));
    }
}

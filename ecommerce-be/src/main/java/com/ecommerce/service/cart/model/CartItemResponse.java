package com.ecommerce.service.cart.model;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Data
public class CartItemResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 4257764010347368886L;
    private Long id;
    private String title;
    private Integer quantity;
    private BigDecimal price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartItemResponse)) return false;
        CartItemResponse that = (CartItemResponse) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void increaseQuantity() {
        if (Objects.isNull(quantity)) {
            quantity = 1;
        } else {
            quantity++;
        }
    }
}

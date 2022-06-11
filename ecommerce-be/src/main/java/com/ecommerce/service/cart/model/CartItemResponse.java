package com.ecommerce.service.cart.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 4257764010347368886L;
    private Long id;
    private String title;
    private Integer quantity = 1;
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

}

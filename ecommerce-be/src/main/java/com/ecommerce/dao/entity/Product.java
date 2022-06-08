package com.ecommerce.dao.entity;

import com.ecommerce.service.merchant.model.ShipmentDeliveryTimes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "product")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(targetEntity = ProductImage.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Set<ProductImage> images;

    @Column
    private String title;
    @Column
    private String sku;
    @Column
    private BigDecimal price;
    @Column
    private Long inventory;
    @Column
    @Enumerated(EnumType.STRING)
    private ShipmentDeliveryTimes shipmentDeliveryTimes;
    @Column
    private Boolean active;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Product{" +
                "sku='" + sku + '\'' +
                ",title='" + title + '\'' +
                ", price=" + price +
                ", inventory=" + inventory +
                ", shipmentDeliveryTimes=" + shipmentDeliveryTimes +
                ", active=" + active +
                '}';
    }
}

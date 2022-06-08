package com.ecommerce.service.product;

import com.ecommerce.service.product.model.ProductSimpleResponse;

import java.util.List;

public interface ProductService {

    List<ProductSimpleResponse> getProducts(int page, int size);
}

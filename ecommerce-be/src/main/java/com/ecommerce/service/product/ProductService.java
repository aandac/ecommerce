package com.ecommerce.service.product;

import com.ecommerce.service.product.model.ProductSimpleResponse;

import java.util.List;

public interface ProductService {

    List<ProductSimpleResponse> getProducts(String category, int page, int size);

    List<String> getAllCategories();
}

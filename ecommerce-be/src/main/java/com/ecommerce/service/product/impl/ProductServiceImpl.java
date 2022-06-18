package com.ecommerce.service.product.impl;

import com.ecommerce.dao.entity.ProductCategory;
import com.ecommerce.dao.entity.ProductImage;
import com.ecommerce.dao.repo.ProductCategoryRepository;
import com.ecommerce.dao.repo.ProductRepository;
import com.ecommerce.service.aws.AwsS3Service;
import com.ecommerce.service.product.ProductService;
import com.ecommerce.service.product.model.ProductSimpleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductCategoryRepository productCategoryRepository;
    private final ProductRepository productRepository;
    private final AwsS3Service awsS3Service;

    @Override
    public List<String> getAllCategories() {
        return productCategoryRepository.findAll(PageRequest.of(0, 100))
                .stream()
                .map(ProductCategory::getCategory)
                .toList();
    }

    @Override
    public List<ProductSimpleResponse> getProducts(String category, int page, int size) {
        if (StringUtils.hasText(category)) {
            return productRepository.getProductsByCategoriesWithImages(category, PageRequest.of(page, size))
                    .stream()
                    .map(p -> new ProductSimpleResponse(
                            p.getId(),
                            Objects.isNull(p.getCategory()) ? null : p.getCategory().getCategory(),
                            p.getTitle(),
                            getImages(p.getImages()),
                            p.getPrice()
                    ))
                    .toList();
        }
        return productRepository.getProductsWithImages(PageRequest.of(page, size))
                .stream()
                .map(p -> new ProductSimpleResponse(
                        p.getId(),
                        Objects.isNull(p.getCategory()) ? null : p.getCategory().getCategory(),
                        p.getTitle(),
                        getImages(p.getImages()),
                        p.getPrice()
                ))
                .toList();
    }

    private List<String> getImages(Set<ProductImage> images) {
        if (CollectionUtils.isEmpty(images)) {
            return Collections.emptyList();
        }
        return images.stream()
                .map(image -> awsS3Service.generateLink(image.getFileName()))
                .toList();
    }
}

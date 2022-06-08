package com.ecommerce.service.product.impl;

import com.ecommerce.dao.entity.ProductImage;
import com.ecommerce.dao.repo.ProductRepository;
import com.ecommerce.service.aws.AwsS3Service;
import com.ecommerce.service.product.ProductService;
import com.ecommerce.service.product.model.ProductSimpleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final AwsS3Service awsS3Service;

    @Override
    public List<ProductSimpleResponse> getProducts(int page, int size) {
        return productRepository.getProductsWithImages(PageRequest.of(page, size))
                .stream()
                .map(p -> new ProductSimpleResponse(
                        p.getId(),
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

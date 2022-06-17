package com.ecommerce.service.merchant.impl;

import com.ecommerce.controller.merchant.model.MerchantProductCreateRequest;
import com.ecommerce.dao.entity.Product;
import com.ecommerce.dao.entity.ProductImage;
import com.ecommerce.dao.entity.User;
import com.ecommerce.dao.repo.ProductImageRepository;
import com.ecommerce.dao.repo.ProductRepository;
import com.ecommerce.service.aws.AwsS3Service;
import com.ecommerce.service.aws.model.AwsS3Object;
import com.ecommerce.service.image.ImageResizerService;
import com.ecommerce.service.merchant.MerchantCreateProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class MerchantCreateProductServiceImpl implements MerchantCreateProductService {
    private final AwsS3Service awsS3Service;
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;

    private final ImageResizerService imageResizerService;

    @Override
    public Long createProduct(User user, MerchantProductCreateRequest request) {
        // save product
        var savedProduct = productRepository.save(Product.builder()
                .user(user)
                .title(request.title())
                .sku(request.sku())
                .inventory(request.inventory())
                .price(request.price())
                .shipmentDeliveryTimes(request.shipmentDeliveryTimes())
                .active(request.active())
                .build());
        // upload file
        if (!CollectionUtils.isEmpty(request.files())) {
            for (String fileName : request.files()) {
                productImageRepository.save(ProductImage.builder()
                        .product(savedProduct)
                        .fileName(fileName)
                        .build());
            }
        }
        return savedProduct.getId();
    }

    @Override
    public String uploadFile(User user, MultipartFile multipartFile) {
        AwsS3Object uploadS3Object;
        try {
            var tempImage = awsS3Service.uploadToS3(
                    UUID.randomUUID().toString(),
                    multipartFile.getInputStream(),
                    true
            );
            var resizedImage = imageResizerService.resizeImage(tempImage.fileUrl());
            String fileName = UUID.randomUUID() + getExtensionByStringHandling(multipartFile.getOriginalFilename()).orElse("");
            uploadS3Object = awsS3Service.uploadToS3(
                    fileName,
                    resizedImage
            );
            awsS3Service.deleteS3Object(tempImage.fileName(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return uploadS3Object.fileName();
    }

    public Optional<String> getExtensionByStringHandling(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".")));
    }
}

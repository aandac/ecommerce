package com.ecommerce.service.merchant.impl;

import com.ecommerce.controller.merchant.model.MerchantProductCreateRequest;
import com.ecommerce.dao.entity.User;
import com.ecommerce.service.aws.AwsS3Service;
import com.ecommerce.service.merchant.MerchantCreateProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MerchantCreateProductServiceImpl implements MerchantCreateProductService {
    private final AwsS3Service awsS3Service;

    @Override
    public void createProduct(User user, MerchantProductCreateRequest request, List<MultipartFile> documents) {
        // save product
        // upload file
        // save file object
    }
}

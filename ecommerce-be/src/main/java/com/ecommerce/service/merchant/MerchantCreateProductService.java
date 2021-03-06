package com.ecommerce.service.merchant;

import com.ecommerce.controller.merchant.model.MerchantProductCreateRequest;
import com.ecommerce.dao.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MerchantCreateProductService {

    Long createProduct(User user, MerchantProductCreateRequest request);

    String uploadFile(User user, MultipartFile multipartFile);
}

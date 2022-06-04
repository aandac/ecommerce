package com.ecommerce.dao.repo;


import com.ecommerce.dao.entity.ProductImage;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends PagingAndSortingRepository<ProductImage, Long> {

}

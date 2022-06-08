package com.ecommerce.dao.repo;


import com.ecommerce.dao.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
    @Query(value = "SELECT distinct p " +
            "FROM Product p " +
            "LEFT JOIN FETCH p.images pi " +
            "WHERE p.active = true",
            countQuery = "SELECT COUNT(distinct p) " +
                    "FROM Product p " +
                    "LEFT JOIN p.images pi " +
                    "WHERE p.active = true")
    Page<Product> getProductsWithImages(Pageable pageable);

}

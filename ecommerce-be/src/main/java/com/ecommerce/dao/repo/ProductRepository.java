package com.ecommerce.dao.repo;


import com.ecommerce.dao.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
    @Query(value = "SELECT distinct p " +
            "FROM Product p " +
            "LEFT JOIN FETCH p.images pi " +
            "LEFT JOIN FETCH p.category pc " +
            "WHERE p.active = true",
            countQuery = "SELECT COUNT(distinct p) " +
                    "FROM Product p " +
                    "LEFT JOIN p.images pi " +
                    "LEFT JOIN p.category pc " +
                    "WHERE p.active = true")
    @EntityGraph(attributePaths = "category")
    Page<Product> getProductsWithImages(Pageable pageable);

    @Query(value = "SELECT distinct p " +
            "FROM Product p " +
            "LEFT JOIN FETCH p.images pi " +
            "LEFT JOIN FETCH p.category pc " +
            "WHERE p.active = true AND pc.category = :category",
            countQuery = "SELECT COUNT(distinct p) " +
                    "FROM Product p " +
                    "LEFT JOIN p.images pi " +
                    "LEFT JOIN p.category pc " +
                    "WHERE p.active = true AND pc.category = :category")
    Page<Product> getProductsByCategoriesWithImages(String category, Pageable pageable);

}

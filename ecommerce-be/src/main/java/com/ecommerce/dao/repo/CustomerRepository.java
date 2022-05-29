package com.ecommerce.dao.repo;


import com.ecommerce.dao.entity.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {

    Optional<Customer> findByEmail(String email);
}

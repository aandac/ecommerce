package com.ecommerce.dao.repo;


import com.ecommerce.dao.entity.CreditCard;
import com.ecommerce.dao.entity.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CreditCardRepository extends PagingAndSortingRepository<CreditCard, Long> {

    Optional<List<CreditCard>> findByCustomer(Customer customer);
}

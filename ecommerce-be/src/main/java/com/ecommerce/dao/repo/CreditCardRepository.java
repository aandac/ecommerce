package com.ecommerce.dao.repo;


import com.ecommerce.dao.entity.CreditCard;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CreditCardRepository extends PagingAndSortingRepository<CreditCard, Long> {
}

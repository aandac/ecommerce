package com.ecommerce.dao.repo;


import com.ecommerce.dao.entity.CreditCard;
import com.ecommerce.dao.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface CreditCardRepository extends PagingAndSortingRepository<CreditCard, Long> {

    Optional<List<CreditCard>> findByUser(User user);

    @Transactional
    @Modifying
    Integer deleteByUser(User user);
}

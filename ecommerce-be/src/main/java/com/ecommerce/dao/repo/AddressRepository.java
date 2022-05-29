package com.ecommerce.dao.repo;


import com.ecommerce.dao.entity.Address;
import com.ecommerce.dao.entity.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends PagingAndSortingRepository<Address, Long> {

    Optional<List<Address>> findByCustomer(Customer customer);
}

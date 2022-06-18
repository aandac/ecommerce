package com.ecommerce.dao.repo;


import com.ecommerce.dao.entity.Address;
import com.ecommerce.dao.entity.AddressType;
import com.ecommerce.dao.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends PagingAndSortingRepository<Address, Long> {

    Optional<List<Address>> findByUser(User user);

    @Transactional
    @Modifying
    Integer deleteByUserAndAddressType(User user, AddressType addressType);
}

package com.ecommerce.dao.repo;


import com.ecommerce.dao.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    @Query(value = "SELECT u " +
            "FROM User u " +
            "JOIN FETCH u.userRoles ur " +
            "WHERE u.email = :email ")
    Optional<User> findByEmailWithRoles(String email);
}

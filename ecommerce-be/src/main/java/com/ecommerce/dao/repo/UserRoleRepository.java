package com.ecommerce.dao.repo;


import com.ecommerce.dao.entity.User;
import com.ecommerce.dao.entity.UserRole;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRoleRepository extends PagingAndSortingRepository<UserRole, Long> {

    Optional<List<UserRole>> findByUser(User user);
}

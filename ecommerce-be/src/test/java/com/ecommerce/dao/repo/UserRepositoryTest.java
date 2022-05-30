package com.ecommerce.dao.repo;

import com.ecommerce.DatabaseTest;
import com.ecommerce.dao.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class UserRepositoryTest extends DatabaseTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void it_should_persist_customer_successfully() {
        // GIVEN
        var customer = User.builder()
                .email("test@test.com")
                .password("password")
                .build();
        // WHEN

        var createdCustomer = userRepository.save(customer);

        // THEN
        var fetchedCustomer = userRepository.findById(createdCustomer.getId());
        assertThat(fetchedCustomer).isPresent();
        assertThat(fetchedCustomer.get().getId()).isEqualTo(createdCustomer.getId());
    }
}

package com.ecommerce.dao.repo;

import com.ecommerce.DatabaseTest;
import com.ecommerce.dao.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerRepositoryTest extends DatabaseTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void it_should_persist_customer_successfully() {
        // GIVEN
        var customer = Customer.builder()
                .email("test@test.com")
                .password("password")
                .build();
        // WHEN

        var createdCustomer = customerRepository.save(customer);

        // THEN
        var fetchedCustomer = customerRepository.findById(createdCustomer.getId());
        assertThat(fetchedCustomer).isPresent();
        assertThat(fetchedCustomer.get().getId()).isEqualTo(createdCustomer.getId());
    }
}

package com.ecommerce.service.customer.impl;

import com.ecommerce.DatabaseTest;
import com.ecommerce.controller.customer.model.CustomerCreateRequest;
import com.ecommerce.dao.repo.AddressRepository;
import com.ecommerce.dao.repo.CreditCardRepository;
import com.ecommerce.dao.repo.UserRepository;
import com.ecommerce.service.customer.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class UserServiceImplTest extends DatabaseTest {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Test
    void it_should_create_customer() {
        // GIVEN
        var request = new CustomerCreateRequest(
                "bulk@test.com",
                "password",
                null,
                null,
                null,
                null,
                null,
                null
        );
        // WHEN
        customerService.createCustomer(request);
        // THEN

        var customers = userRepository.findAll();
        assertThat(customers.iterator().next()).isNotNull();
    }

    @Test
    void it_should_create_customer_with_address_successfully() {
        // GIVEN
        var request = new CustomerCreateRequest(
                "bulk_address@test.com",
                "password",
                "billing test address",
                null,
                null,
                null,
                null,
                null
        );
        // WHEN
        customerService.createCustomer(request);
        // THEN

        var customer = userRepository.findByEmailWithRoles(request.email());
        assertThat(customer).isPresent();
        assertThat(customer.get()).isNotNull();
        var addressList = addressRepository.findByUser(customer.get());
        assertThat(addressList).isPresent();
        assertThat(addressList.get()).hasSize(1);
    }

    @Test
    void it_should_create_customer_with_multiple_address_successfully() {
        // GIVEN
        var request = new CustomerCreateRequest(
                "bulk_address_multiple@test.com",
                "password",
                "billing test address",
                "shipping test address",
                null,
                null,
                null,
                null
        );
        // WHEN
        customerService.createCustomer(request);
        // THEN

        var customer = userRepository.findByEmailWithRoles(request.email());
        assertThat(customer).isPresent();
        assertThat(customer.get()).isNotNull();
        var addressList = addressRepository.findByUser(customer.get());
        assertThat(addressList).isPresent();
        assertThat(addressList.get()).hasSize(2);

        var cardList = creditCardRepository.findByUser(customer.get());
        assertThat(cardList).isPresent();
        assertThat(cardList.get()).isEmpty();
    }

    @Test
    void it_should_create_customer_with_credit_card_successfully() {
        // GIVEN
        var request = new CustomerCreateRequest(
                "bulk_address_multiple_with_card@test.com",
                "password",
                "billing test address",
                "shipping test address",
                "4242424242424242", (short) 12,
                (short) 2023,
                (short) 845
        );
        // WHEN
        customerService.createCustomer(request);

        // THEN
        var customer = userRepository.findByEmailWithRoles(request.email());
        assertThat(customer).isPresent();
        assertThat(customer.get()).isNotNull();
        var addressList = addressRepository.findByUser(customer.get());
        assertThat(addressList).isPresent();
        assertThat(addressList.get()).hasSize(2);

        var cardList = creditCardRepository.findByUser(customer.get());
        assertThat(cardList).isPresent();
        assertThat(cardList.get()).hasSize(1);
    }
}

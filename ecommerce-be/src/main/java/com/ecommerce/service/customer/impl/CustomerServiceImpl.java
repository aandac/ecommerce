package com.ecommerce.service.customer.impl;

import com.ecommerce.controller.customer.model.CustomerCreateRequest;
import com.ecommerce.dao.entity.Address;
import com.ecommerce.dao.entity.AddressType;
import com.ecommerce.dao.entity.CreditCard;
import com.ecommerce.dao.entity.Customer;
import com.ecommerce.dao.repo.AddressRepository;
import com.ecommerce.dao.repo.CreditCardRepository;
import com.ecommerce.dao.repo.CustomerRepository;
import com.ecommerce.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;
    private final CreditCardRepository creditCardRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public void createCustomer(CustomerCreateRequest request) {
        var customer = Customer.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .build();
        customerRepository.save(customer);

        if (StringUtils.hasText(request.billingAddress())) {
            addressRepository.save(
                    Address.builder()
                            .addressType(AddressType.BILLING_ADDRESS)
                            .addressLine(request.billingAddress())
                            .customer(customer)
                            .build());
        }
        if (StringUtils.hasText(request.shippingAddress())) {
            addressRepository.save(
                    Address.builder()
                            .addressType(AddressType.SHIPPING_ADDRESS)
                            .addressLine(request.shippingAddress())
                            .customer(customer)
                            .build());
        }

        if (validCreditCard(request)) {
            creditCardRepository.save(
                    CreditCard.builder()
                            .creditCardNumber(request.creditCardNumber())
                            .creditCardMonth(request.creditCardExpireMonth())
                            .creditCardYear(request.creditCardExpireYear())
                            .creditCardCvv(request.creditCardCvv())
                            .build());
        }
    }

    private boolean validCreditCard(CustomerCreateRequest request) {
        return StringUtils.hasText(request.creditCardNumber())
                && Objects.nonNull(request.creditCardExpireMonth())
                && request.creditCardExpireMonth() > 0
                && Objects.nonNull(request.creditCardExpireYear())
                && request.creditCardExpireYear() > 0
                && Objects.nonNull(request.creditCardCvv())
                && request.creditCardCvv() > 0;
    }
}

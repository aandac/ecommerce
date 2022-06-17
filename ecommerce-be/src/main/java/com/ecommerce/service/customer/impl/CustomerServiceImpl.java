package com.ecommerce.service.customer.impl;

import com.ecommerce.controller.customer.model.CustomerCreateRequest;
import com.ecommerce.dao.Roles;
import com.ecommerce.dao.entity.*;
import com.ecommerce.dao.repo.AddressRepository;
import com.ecommerce.dao.repo.CreditCardRepository;
import com.ecommerce.dao.repo.UserRoleRepository;
import com.ecommerce.dao.repo.UserRepository;
import com.ecommerce.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final CreditCardRepository creditCardRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void createCustomer(CustomerCreateRequest request) {
        if (userRepository.findByEmailWithRoles(request.email()).isPresent()) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Email already in used");
        }

        var customer = User.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .build();
        userRepository.save(customer);

        userRoleRepository.save(UserRole.builder()
                .user(customer)
                .role(Roles.CUSTOMER)
                .build());

        if (StringUtils.hasText(request.billingAddress())) {
            addressRepository.save(
                    Address.builder()
                            .addressType(AddressType.BILLING_ADDRESS)
                            .addressLine(request.billingAddress())
                            .user(customer)
                            .build());
        }
        if (StringUtils.hasText(request.shippingAddress())) {
            addressRepository.save(
                    Address.builder()
                            .addressType(AddressType.SHIPPING_ADDRESS)
                            .addressLine(request.shippingAddress())
                            .user(customer)
                            .build());
        }

        if (validCreditCard(request)) {
            creditCardRepository.save(
                    CreditCard.builder()
                            .creditCardNumber(request.creditCardNumber())
                            .creditCardMonth(request.creditCardExpireMonth())
                            .creditCardYear(request.creditCardExpireYear())
                            .creditCardCvv(request.creditCardCvv())
                            .user(customer)
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

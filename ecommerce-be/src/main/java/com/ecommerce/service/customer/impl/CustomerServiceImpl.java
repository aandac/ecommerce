package com.ecommerce.service.customer.impl;

import com.ecommerce.controller.customer.model.CustomerCreateRequest;
import com.ecommerce.controller.customer.model.CustomerDetailResponse;
import com.ecommerce.controller.customer.model.CustomerDetailUpdateRequest;
import com.ecommerce.dao.Roles;
import com.ecommerce.dao.entity.*;
import com.ecommerce.dao.repo.AddressRepository;
import com.ecommerce.dao.repo.CreditCardRepository;
import com.ecommerce.dao.repo.UserRepository;
import com.ecommerce.dao.repo.UserRoleRepository;
import com.ecommerce.service.customer.CustomerService;
import liquibase.repackaged.org.apache.commons.collections4.CollectionUtils;
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

    @Override
    public void checkOneClickData(User user) {
        if (user.getUserRoles().stream().noneMatch(role -> role.getRole().name().equals(Roles.CUSTOMER.name()))) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Invalid user type");
        }

        var addressList = addressRepository.findByUser(user);
        addressList.orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "No address provided"));

        addressList.get()
                .stream()
                .filter(address -> address.getAddressType().equals(AddressType.BILLING_ADDRESS))
                .findAny()
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Billing address not provided"));


        addressList.get()
                .stream()
                .filter(address -> address.getAddressType().equals(AddressType.SHIPPING_ADDRESS))
                .findAny()
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Shipping address not provided"));

        creditCardRepository.findByUser(user)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Credit cart not provided"));

    }

    @Override
    public CustomerDetailResponse getCustomerDetail(User user) {
        var builder = CustomerDetailResponse.builder();
        builder.userId(user.getId());
        var addressList = addressRepository.findByUser(user);
        if (addressList.isPresent()) {
            for (Address address : addressList.get()) {
                if (address.getAddressType().equals(AddressType.BILLING_ADDRESS)) {
                    builder.billingAddress(address.getAddressLine());
                } else if (address.getAddressType().equals(AddressType.SHIPPING_ADDRESS)) {
                    builder.shippingAddress(address.getAddressLine());
                } else {
                    throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Invalid address type received");
                }
            }
        }

        var cardList = creditCardRepository.findByUser(user);
        if (cardList.isPresent() && !CollectionUtils.isEmpty(cardList.get())) {
            var creditCard = cardList.get().iterator().next();
            builder.creditCardNumber(creditCard.getCreditCardNumber())
                    .creditCardExpireMonth(creditCard.getCreditCardMonth())
                    .creditCardExpireYear(creditCard.getCreditCardYear())
                    .creditCardCvv(creditCard.getCreditCardCvv());
        }

        return builder.build();
    }

    @Override
    public void updateCustomerDetail(User customer, CustomerDetailUpdateRequest request) {
        if (StringUtils.hasText(request.billingAddress())) {
            addressRepository.deleteByUserAndAddressType(customer, AddressType.BILLING_ADDRESS);
            addressRepository.save(
                    Address.builder()
                            .addressType(AddressType.BILLING_ADDRESS)
                            .addressLine(request.billingAddress())
                            .user(customer)
                            .build());
        }
        if (StringUtils.hasText(request.shippingAddress())) {
            addressRepository.deleteByUserAndAddressType(customer, AddressType.SHIPPING_ADDRESS);
            addressRepository.save(
                    Address.builder()
                            .addressType(AddressType.SHIPPING_ADDRESS)
                            .addressLine(request.shippingAddress())
                            .user(customer)
                            .build());
        }

        if (validCreditCard(request)) {
            creditCardRepository.deleteByUser(customer);
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
        return doValidateCreditCart(
                request.creditCardNumber(),
                request.creditCardExpireMonth(),
                request.creditCardExpireYear(),
                request.creditCardCvv()
        );
    }

    private boolean doValidateCreditCart(
            String creditCardNumber,
            Short expireMonth,
            Short expireYear,
            Short cvv
    ) {
        return Objects.nonNull(creditCardNumber)
                && Objects.nonNull(expireMonth)
                && expireMonth > 0
                && Objects.nonNull(expireYear)
                && expireYear > 0
                && Objects.nonNull(cvv)
                && cvv > 0;
    }

    private boolean validCreditCard(CustomerDetailUpdateRequest request) {
        return doValidateCreditCart(
                request.creditCardNumber(),
                request.creditCardExpireMonth(),
                request.creditCardExpireYear(),
                request.creditCardCvv()
        );
    }
}

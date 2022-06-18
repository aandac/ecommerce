package com.ecommerce.service.cart.impl;

import com.ecommerce.dao.entity.User;
import com.ecommerce.dao.repo.ProductRepository;
import com.ecommerce.service.cart.CartService;
import com.ecommerce.service.cart.CheckoutService;
import com.ecommerce.service.cart.model.CartItemResponse;
import com.ecommerce.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;


@Slf4j
@Service
@RequiredArgsConstructor
public class CheckoutServiceImpl implements CheckoutService {

    private final CartService cartService;
    private final CustomerService customerService;
    private final ProductRepository productRepository;

    @Override
    public BigDecimal oneClickCheckout(User user) {
        var cartItems = cartService.getCartItems(user.getEmail());
        if (CollectionUtils.isEmpty(cartItems)) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Cart is empty");
        }

        customerService.checkOneClickData(user);

        BigDecimal totalAmount = BigDecimal.ZERO;
        for (CartItemResponse cartItem : cartItems) {
            var product = productRepository.findById(cartItem.getId());
            if (product.isPresent()) {
                var lineTotal = product.get().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
                totalAmount = totalAmount.add(lineTotal);
            }
        }
        cartService.deleteAllCartItems(user.getEmail());
        return totalAmount;
    }
}

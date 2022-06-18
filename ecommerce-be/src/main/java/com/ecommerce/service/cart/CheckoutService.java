package com.ecommerce.service.cart;

import com.ecommerce.dao.entity.User;

import java.math.BigDecimal;

public interface CheckoutService {

    BigDecimal oneClickCheckout(User user);
}

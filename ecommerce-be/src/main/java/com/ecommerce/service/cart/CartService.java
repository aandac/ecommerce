package com.ecommerce.service.cart;

import com.ecommerce.service.cart.model.CartItemResponse;

import java.util.List;

public interface CartService {

    List<CartItemResponse> getCartItems(String id);

    void addCartItem(String id ,CartItemResponse itemResponse);

    void deleteCartItem(String id, CartItemResponse itemResponse);
}

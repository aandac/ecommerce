package com.ecommerce.service.cart;

import com.ecommerce.service.cart.model.CartItemResponse;

import java.util.List;

public interface CartService {

    List<CartItemResponse> getCartItems(String id);

    List<CartItemResponse> addCartItem(String id ,CartItemResponse itemResponse);

    List<CartItemResponse> deleteCartItem(String id, String itemResponse);

    void deleteAllCartItems(String id);
}

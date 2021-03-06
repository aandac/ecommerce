package com.ecommerce.service.cart.impl;

import com.ecommerce.service.cart.CartService;
import com.ecommerce.service.cart.model.CartItemResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final RedisTemplate<String, List<CartItemResponse>> redisTemplate;

    @Override
    public List<CartItemResponse> getCartItems(String id) {
        var cart = redisTemplate.opsForHash().get("CART", id);
        if (Objects.isNull(cart)) {
            return Collections.emptyList();
        }

        return (List<CartItemResponse>) cart;
    }

    @Override
    public List<CartItemResponse> addCartItem(String id, CartItemResponse itemResponse) {
        var cart = redisTemplate.opsForHash().get("CART", id);
        List<CartItemResponse> list;
        if (Objects.isNull(cart)) {
            list = new ArrayList<>();
        } else {
            list = (List<CartItemResponse>) cart;
        }
        var previousAddedCartItem = list.stream()
                .filter(item -> item.getId().equals(itemResponse.getId()))
                .findAny();
        if (previousAddedCartItem.isPresent()) {
            previousAddedCartItem.get().setQuantity(
                    previousAddedCartItem.get()
                            .getQuantity() +
                            itemResponse.getQuantity()
            );
        } else {
            list.add(itemResponse);
        }
        redisTemplate.opsForHash().put("CART", id, list);
        return list;
    }

    @Override
    public List<CartItemResponse> deleteCartItem(String id, String productId) {
        var cart = redisTemplate.opsForHash().get("CART", id);
        if (Objects.isNull(cart)) {
            return null;
        }
        List<CartItemResponse> list = (List<CartItemResponse>) cart;
        if (Objects.isNull(productId)) {
            list.clear();
        } else {
            list.stream()
                    .filter(item -> item.getId().equals(Long.valueOf(productId)))
                    .findAny()
                    .ifPresent(list::remove);
        }

        redisTemplate.opsForHash().put("CART", id, list);
        return list;
    }


    @Override
    public void deleteAllCartItems(String id) {
        redisTemplate.opsForHash().delete("CART", id);
    }
}

package com.example.service;
import com.example.dto.cartitemrequest;
import com.example.model.Cart;
import com.example.model.CartItem;
import jakarta.validation.Valid;

import java.util.List;

public interface cartservice {
    Cart createCart(Integer userId);

    Cart getCartById(Integer cartId);

    Cart addItemToCart(Integer cartId, CartItem item);

    List<CartItem> getCartItems(Integer cartId);

    void addToCart(Integer cartId, Integer productId, Integer quantity);

    void addToCart(@Valid cartitemrequest request);
}

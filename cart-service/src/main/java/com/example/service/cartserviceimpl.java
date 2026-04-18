
package com.example.service;

import com.example.dto.ProductDTO;
import com.example.model.Cart;
import com.example.model.CartItem;
import com.example.repository.cartrepository;
import com.example.repository.cartitemrepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class cartserviceimpl implements cartservice {

    private final cartrepository cartRepository;
    private final cartitemrepository cartItemRepository;
    private final WebClient webClient;

    // ✅ ONLY ONE constructor
    public cartserviceimpl(cartrepository cartRepository,
                           cartitemrepository cartItemRepository,
                           WebClient webClient) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.webClient = webClient;
    }

    // ✅ FIXED (no generics misuse)
    public ProductDTO getProductById(Integer productId) {
        return webClient.get()
                .uri("/api/products/{id}", productId) // baseUrl already set
                .retrieve()
                .bodyToMono(ProductDTO.class)
                .block();
    }

    // ✅ Validation logic
    public void validateProduct(Integer productId, Integer quantity) {
        ProductDTO product = getProductById(productId);

        if (product == null) {
            throw new RuntimeException("Product with ID " + productId + " not found.");
        }

        if (product.getStock() < quantity) {
            throw new RuntimeException("Insufficient stock for product ID " + productId);
        }
    }

    // ✅ Add to cart (main logic)
    public void addToCart(Integer cartId, Integer productId, Integer quantity) {

        validateProduct(productId, quantity);

        Cart cart = getCartById(cartId);
        if (cart == null) {
            throw new RuntimeException("Cart with ID " + cartId + " not found.");
        }

        CartItem item = new CartItem();
        item.setProductId(Long.valueOf(productId));
        item.setQuantity(quantity);
        item.setCart(cart);

        cartItemRepository.save(item);
    }

    @Override
    public Cart createCart(Integer userId) {
        Cart cart = new Cart();
        cart.setUserId(Long.valueOf(userId));
        return cartRepository.save(cart);
    }

    @Override
    public Cart getCartById(Integer cartId) {
        return cartRepository.findById(cartId).orElse(null);
    }

    @Override
    public Cart addItemToCart(Integer cartId, CartItem item) {
        Cart cart = getCartById(cartId);
        if (cart != null) {
            item.setCart(cart);
            cartItemRepository.save(item);
            return getCartById(cartId);
        }
        return null;
    }

    @Override
    public List<CartItem> getCartItems(Integer cartId) {
        Cart cart = getCartById(cartId);
        return cart != null ? cart.getItems() : null;
    }
}
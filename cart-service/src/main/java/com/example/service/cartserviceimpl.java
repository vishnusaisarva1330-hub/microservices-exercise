
package com.example.service;

import com.example.dto.ProductDTO;
import com.example.dto.cartevent;
import com.example.model.Cart;
import com.example.model.CartItem;
import com.example.repository.cartrepository;
import com.example.repository.cartitemrepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class cartserviceimpl implements cartservice {

    private final cartrepository cartRepository;
    private final cartitemrepository cartItemRepository;
    private final WebClient webClient;
    private final kafkaproducerservice kafkaProducerService;

    // ✅ Constructor using WebClient.Builder
    public cartserviceimpl(cartrepository cartRepository,
                           cartitemrepository cartItemRepository,
                           WebClient.Builder webClientBuilder,
                           kafkaproducerservice kafkaProducerService) {

        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.webClient = webClientBuilder.baseUrl("http://localhost:8081").build();
        this.kafkaProducerService = kafkaProducerService;
    }

    // ✅ Async Product Fetch
    public CompletableFuture<ProductDTO> getProductAsync(Integer productId) {
        return CompletableFuture.supplyAsync(() ->
                webClient.get()
                        .uri("/api/products/" + productId)
                        .retrieve()
                        .bodyToMono(ProductDTO.class)
                        .block()
        );
    }

    // ✅ Main Method (Async + DB + Kafka)
    @Override
    public void addToCart(Integer cartId, Integer productId, Integer quantity) {

        System.out.println("STEP 1: Start");

        CompletableFuture<ProductDTO> productFuture = getProductAsync(productId);

        CompletableFuture<Boolean> stockFuture = productFuture.thenApplyAsync(product -> {
            if (product == null) {
                throw new RuntimeException("Product not found");
            }
            return product.getStock() >= quantity;
        });

        CompletableFuture<Void> combinedFuture = productFuture.thenCombine(stockFuture,
                (product, isStockAvailable) -> {

                    if (!isStockAvailable) {
                        throw new RuntimeException("Insufficient stock");
                    }

                    System.out.println("STEP 2: Product OK");

                    Cart cart = getCartById(cartId);
                    if (cart == null) {
                        throw new RuntimeException("Cart not found");
                    }

                    System.out.println("STEP 3: Cart OK");

                    CartItem item = new CartItem();
                    item.setProductId(Long.valueOf(productId));
                    item.setQuantity(quantity);
                    item.setCart(cart);

                    cartItemRepository.save(item);
                    System.out.println("STEP 4: Item saved");

                    cartevent event = new cartevent(cartId, productId, quantity);
                    kafkaProducerService.sendCartEvent(event);

                    System.out.println("STEP 5: Kafka sent");

                    return null;
                });

        // ✅ Wait for completion
        combinedFuture.join();
    }

    // ✅ Other methods (unchanged)

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
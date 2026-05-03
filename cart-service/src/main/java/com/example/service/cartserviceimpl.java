package com.example.service;

import com.example.dto.cartitemrequest;
import com.example.dto.ProductDTO;
import com.example.exception.badrequestexception;
import com.example.exception.resourcenotfoundexception;
import com.example.model.Cart;
import com.example.model.CartItem;
import com.example.repository.cartitemrepository;
import com.example.repository.cartrepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
public class cartserviceimpl implements cartservice {

    private final cartrepository cartRepository;
    private final cartitemrepository cartItemRepository;
    private final WebClient webClient;

    public cartserviceimpl(cartrepository cartRepository,
                           cartitemrepository cartItemRepository,
                           WebClient webClient) {

        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.webClient = webClient;
    }

    // ✅ GET PRODUCT FROM PRODUCT SERVICE
    private ProductDTO getProduct(Integer productId) {
        return webClient.get()
                .uri("/api/products/" + productId)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(),
                        response -> Mono.error(new resourcenotfoundexception("Product not found")))
                .onStatus(status -> status.is5xxServerError(),
                        response -> Mono.error(new RuntimeException("Product service error")))
                .bodyToMono(ProductDTO.class)
                .block();
    }

    // ✅ MAIN METHOD
    @Override
    public void addToCart(cartitemrequest request) {

        log.info("Adding product {} with quantity {} to cart",
                request.getProductId(), request.getQuantity());

        // ✅ Quantity validation
        if (request.getQuantity() <= 0) {
            throw new badrequestexception("Quantity must be greater than zero");
        }

        // 🔥 CALL PRODUCT SERVICE
        ProductDTO product = getProduct(request.getProductId());
        if (product == null) {
            log.error("Product not found: {}", request.getProductId());
            throw new resourcenotfoundexception("Product not found");
        }
        // 🚨 STOCK VALIDATION
        if (product.getStock() < request.getQuantity()) {
            throw new badrequestexception("Insufficient stock");
        }

        Long userId = 101L;

        // ✅ GET OR CREATE CART
        Cart cart = (Cart) cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUserId(userId);
                    return cartRepository.save(newCart);
                });

        // ✅ CREATE ITEM
        CartItem item = CartItem.builder()
                .productId(Long.valueOf(request.getProductId()))
                .quantity(request.getQuantity())
                .cart(cart)
                .build();

        // ✅ SAVE
        cartItemRepository.save(item);

        log.info("Successfully added product {} to cart {}",
                request.getProductId(), cart.getId());
    }

    // OPTIONAL METHODS (keep if needed)

    @Override
    public Cart createCart(Integer userId) {
        return (Cart) cartRepository.findByUserId(userId.longValue())
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setUserId(userId.longValue());
                    return cartRepository.save(cart);
                });
    }

    @Override
    public Cart getCartById(Integer cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
    }

    @Override
    public List<CartItem> getCartItems(Integer cartId) {
        return cartItemRepository.findByCart_Id(cartId.longValue());
    }

    @Override
    public Cart addItemToCart(Integer cartId, CartItem item) {
        return null;
    }

    @Override
    public void addToCart(Integer cartId, Integer productId, Integer quantity) {
        // not used
    }
}



















//package com.example.service;
//
//import com.example.dto.cartitemrequest;
//import com.example.model.Cart;
//import com.example.model.CartItem;
//import com.example.repository.cartitemrepository;
//import com.example.repository.cartrepository;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@Slf4j
//public class cartserviceimpl implements cartservice {
//
//    private final cartrepository cartRepository;
//    private final cartitemrepository cartItemRepository;
//
//    public cartserviceimpl(cartrepository cartRepository,
//                           cartitemrepository cartItemRepository) {
//        this.cartRepository = cartRepository;
//        this.cartItemRepository = cartItemRepository;
//    }
//
//    // ✅ CREATE OR GET CART (IMPORTANT FIX)
//    @Override
//    public Cart createCart(Integer userId) {
//
//        return (Cart) cartRepository.findByUserId(userId.longValue())
//                .orElseGet(() -> {
//                    Cart cart = new Cart();
//                    cart.setUserId(userId.longValue());
//                    return cartRepository.save(cart);
//                });
//    }
//
//    // ✅ GET CART
//    @Override
//    public Cart getCartById(Integer cartId) {
//        return cartRepository.findById(cartId)
//                .orElseThrow(() -> new RuntimeException("Cart not found"));
//    }
//
//    // ✅ GET CART ITEMS
//    @Override
//    public List<CartItem> getCartItems(Integer cartId) {
//        return cartItemRepository.findByCartId(cartId);
//    }
//
//    // ❌ NOT USED (KEEP EMPTY OR REMOVE)
//    @Override
//    public Cart addItemToCart(Integer cartId, CartItem item) {
//        return null;
//    }
//
//    @Override
//    public void addToCart(Integer cartId, Integer productId, Integer quantity) {
//        // optional
//    }
//
//    // 🔥 MAIN METHOD (FIXED)
//    @Override
//    public void addToCart(cartitemrequest request) {
//
//        log.info("Adding product {} with quantity {} to cart",
//                request.getProductId(), request.getQuantity());
//
//        // ✅ Validation
//        if (request.getQuantity() <= 0) {
//            log.error("Invalid quantity: {}", request.getQuantity());
//            throw new RuntimeException("Quantity must be greater than zero");
//        }
//
//        Long userId = 101L; // 🔥 later replace with actual user
//
//        // ✅ Get or create cart
//        Cart cart = (Cart) cartRepository.findByUserId(userId)
//                .orElseGet(() -> {
//                    Cart newCart = new Cart();
//                    newCart.setUserId(userId);
//                    return cartRepository.save(newCart);
//                });
//
//        // ✅ Create CartItem
//        CartItem item = CartItem.builder()
//                .productId(Long.valueOf(request.getProductId()))
//                .quantity(request.getQuantity())
//                .cart(cart)   // 🔥 THIS FIXES NULL ISSUE
//                .build();
//
//        // ✅ Save
//        cartItemRepository.save(item);
//
//        log.info("Successfully added product {} to cart {}",
//                request.getProductId(), cart.getId());
//    }
//}
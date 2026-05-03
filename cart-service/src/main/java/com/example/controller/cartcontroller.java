//package com.example.controller;
//import com.example.dto.cartitemrequest;
//import com.example.service.cartservice;
//import com.example.model.Cart;
//import com.example.model.CartItem;
//import jakarta.validation.Valid;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import java.util.List;
//@Slf4j
//@RestController
//@RequestMapping("/api/cart")
//public class cartcontroller {
//
//    private final cartservice cartService;
//
//    public cartcontroller(cartservice cartService) {
//        this.cartService = cartService;
//    }
//    @PostMapping("/add")
//    public ResponseEntity<String> addToCart(@Valid @RequestBody cartitemrequest request) {
//        log.info("Received request to add product {} with quantity {} to cart", request.getProductId(), request.getQuantity());
//        cartService.addToCart(request);
//        return ResponseEntity.ok("Item added to cart successfully");
//    }
//
//
//
//
//
//
//
////    @PostMapping("/add")
////    public ResponseEntity<String> addToCart(@Valid @RequestBody cartitemrequest request) {
////        cartService.addToCart(request);
////        return ResponseEntity.ok("Item added to cart ");
////    }
//
//
//    @PostMapping("/add")
//    public String addToCart(@RequestBody CartItem item) {
//        cartService.addToCart(Math.toIntExact(item.getCartId()),
//                Math.toIntExact(item.getProductId()),
//                item.getQuantity());
//        return "Item added to cart successfully";
//
//    }
//
//    @PostMapping("/create")
//    public Cart createCart(@RequestParam Integer userId) {
//        return cartService.createCart(userId);
//    }
//
//    @GetMapping("/{cartId}")
//    public Cart getCartById(@PathVariable Integer cartId) {
//        return cartService.getCartById(cartId);
//    }
//
//
//
//
//    @GetMapping("/{cartId}/items")
//    public List<CartItem> getCartItems(@PathVariable Integer cartId) {
//        return cartService.getCartItems(cartId);
//    }
//}
package com.example.controller;

import com.example.dto.cartitemrequest;
import com.example.service.cartservice;
import com.example.model.Cart;
import com.example.model.CartItem;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/cart")
public class cartcontroller {

    private final cartservice cartService;

    public cartcontroller(cartservice cartService) {
        this.cartService = cartService;
    }

    // ✅ ONLY ONE ADD METHOD (DTO BASED)
    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@Valid @RequestBody cartitemrequest request) {

        log.info("API CALL → Add to cart | productId={} quantity={}",
                request.getProductId(), request.getQuantity());

        cartService.addToCart(request);

        return ResponseEntity.ok("Item added to cart successfully");
    }

    // ✅ CREATE CART
    @PostMapping("/create")
    public Cart createCart(@RequestParam Integer userId) {

        log.info("Creating cart for user {}", userId);

        return cartService.createCart(userId);
    }

    // ✅ GET CART
    @GetMapping("/{cartId}")
    public Cart getCartById(@PathVariable Integer cartId) {

        log.info("Fetching cart {}", cartId);

        return cartService.getCartById(cartId);
    }

    // ✅ GET CART ITEMS
    @GetMapping("/{cartId}/items")
    public List<CartItem> getCartItems(@PathVariable Integer cartId) {

        log.info("Fetching items for cart {}", cartId);

        return cartService.getCartItems(cartId);
    }
}
package com.example.controller;
import com.example.service.cartservice;
import com.example.model.Cart;
import com.example.model.CartItem;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/cart")
public class cartcontroller {

    private final cartservice cartService;

    public cartcontroller(cartservice cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/create")
    public Cart createCart(@RequestParam Integer userId) {
        return cartService.createCart(userId);
    }

    @GetMapping("/{cartId}")
    public Cart getCartById(@PathVariable Integer cartId) {
        return cartService.getCartById(cartId);
    }

//    @PostMapping("/{cartId}/add")
//    public Cart addItemToCart(@PathVariable Integer cartId, @RequestBody CartItem item) {
//        return cartService.addItemToCart(cartId, item);
@PostMapping("/add")
public Cart addItemToCart(@RequestBody CartItem item) {
    return cartService.addItemToCart(Math.toIntExact(item.getCartId()), item);
    }

    @GetMapping("/{cartId}/items")
    public List<CartItem> getCartItems(@PathVariable Integer cartId) {
        return cartService.getCartItems(cartId);
    }
}

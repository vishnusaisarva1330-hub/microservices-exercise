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

//    @PostMapping("/add")
//    public String addItemToCart(@RequestParam Integer cartId, @RequestBody CartItem item) {
//        cartService.addItemToCart(cartId, item);
//        return "Item added to cart successfully";
//    }

    @PostMapping("/add")
    public String addToCart(@RequestParam Integer cartId,
                            @RequestParam Integer productId,
                            @RequestParam Integer quantity) {

        cartService.addToCart(cartId, productId, quantity);
        return "Item added to cart successfully";
    }

    @PostMapping("/create")
    public Cart createCart(@RequestParam Integer userId) {
        return cartService.createCart(userId);
    }

    @GetMapping("/{cartId}")
    public Cart getCartById(@PathVariable Integer cartId) {
        return cartService.getCartById(cartId);
    }


//@PostMapping("/add")
//public Cart addItemToCart(@RequestBody CartItem item) {
//    return cartService.addItemToCart(Math.toIntExact(item.getCartId()), item);
//    }

    @GetMapping("/{cartId}/items")
    public List<CartItem> getCartItems(@PathVariable Integer cartId) {
        return cartService.getCartItems(cartId);
    }
}

package com.example.service;
import com.example.model.Cart;
import com.example.model.CartItem;
import com.example.repository.cartrepository;
import com.example.repository.cartitemrepository;
import org.springframework.stereotype.Service;
import java.util.List;
@Service

public class cartserviceimpl implements cartservice {

    private final cartrepository cartRepository;
    private final cartitemrepository cartItemRepository;

    public cartserviceimpl(cartrepository cartRepository, cartitemrepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public Cart createCart(Integer userId) {
        Cart cart = new Cart();
        cart.setUserId(Long.valueOf(userId));
        return cartRepository.save(cart);
    }

    @Override
    public Cart getCartById(Integer cartId) {
        return (Cart) cartRepository.findById(cartId).orElse(null);
    }

    @Override
    public Cart addItemToCart(Integer cartId, CartItem item) {
        Cart cart = getCartById(cartId);
        if (cart != null) {
            item.setCart(cart);
            cartItemRepository.save(item);
            return getCartById(cartId); // Return updated cart
        }
        return null;
    }

    @Override
    public List<CartItem> getCartItems(Integer cartId) {
        Cart cart = getCartById(cartId);
        if (cart != null) {
            return cart.getItems();
        }
        return null;
    }
}

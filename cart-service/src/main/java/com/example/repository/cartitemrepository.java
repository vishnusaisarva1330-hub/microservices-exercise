package com.example.repository;
import com.example.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
public interface cartitemrepository extends JpaRepository<CartItem, Integer> {
//    void save(CartItem item);
//    List<CartItem> findByCartId(Integer cartId);
}

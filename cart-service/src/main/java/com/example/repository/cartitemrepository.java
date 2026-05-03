package com.example.repository;
import com.example.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface cartitemrepository extends JpaRepository<CartItem, Integer> {
    List<CartItem> findByCartId(Integer cartId);

    List<CartItem> findByCart_Id(long l);
//    void save(CartItem item);
//List<CartItem> findByCartId(Integer cartId);
}

package com.example.repository;
import com.example.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface cartitemrepository extends JpaRepository<CartItem, Integer> {
}

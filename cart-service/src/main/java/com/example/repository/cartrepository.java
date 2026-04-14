package com.example.repository;
import com.example.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface cartrepository extends JpaRepository<Cart, Integer> {

}

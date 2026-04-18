package com.example.repository;
import com.example.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query(value = "SELECT * FROM products WHERE price > :price", nativeQuery = true)
    List<Product> findProductsByPriceGreaterThan(@Param("price") Double price);
}

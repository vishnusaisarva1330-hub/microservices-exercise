package com.example.service;
import com.example.entity.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface productservice {

    Product createProduct(Product product);

    Product getProductById(Integer id);

    List<Product> getAllProducts();

    boolean isProductInStock(Integer productId, Integer quantity);

    Page<Product> getProducts(int page, int size, String sortBy);

    List<String> getAvailableProductNames();
}

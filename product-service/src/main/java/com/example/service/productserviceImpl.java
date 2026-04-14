package com.example.service;
import com.example.entity.Product;
import com.example.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;
@Service

public class productserviceImpl implements productservice {

    private final ProductRepository productRepository;

    public productserviceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product getProductById(Integer id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public boolean isProductInStock(Integer productId, Integer quantity) {
        Product product = getProductById(productId);
        return product != null && product.getStock() >= quantity;
    }
}

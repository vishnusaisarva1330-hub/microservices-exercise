package com.example.service;
import com.example.dto.productrequest;
import com.example.entity.Product;
import com.example.repository.ProductRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
@Service

public class productserviceImpl implements productservice {

    private final ProductRepository productRepository;

    public productserviceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public Page<Product> getProducts(int page, int size, String sortBy) {
         Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
//        Pageable pageable = PageRequest.of(page, size);
//        Page<Product> productPage = productRepository.findAll(pageable);
//        return (Page<Product>) productPage.getContent();
    return productRepository.findAll(pageable);
    }

    public List<String> getAvailableProductNames() {
        return productRepository.findAll()
                 .stream()
                .filter(product -> product.getStock() > 0)
                .map(Product::getName)
                .collect(Collectors.toList());
    }


    @Override
    public Product createProduct(productrequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        return productRepository.save(product);
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

    @Override
    public List<Product> getProductsByPriceGreaterThan(Double price) {
        return productRepository.findProductsByPriceGreaterThan(price);}

}

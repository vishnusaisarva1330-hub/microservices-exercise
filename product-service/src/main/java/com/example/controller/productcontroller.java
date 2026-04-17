package com.example.controller;
import com.example.service.productservice;
import com.example.entity.Product;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/products")

public class productcontroller {
    private final productservice productService;

    public productcontroller(productservice productService) {
        this.productService = productService;
    }

    // ✅ CREATE PRODUCT
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    // ✅ GET PRODUCT BY ID
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Integer id) {
        return productService.getProductById(id);
    }

    // ✅ GET ALL PRODUCTS
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // ✅ UPDATE PRODUCT
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Integer id,
                                 @RequestBody Product updatedProduct) {

        Product existing = productService.getProductById(id);

        existing.setName(updatedProduct.getName());
        existing.setPrice(updatedProduct.getPrice());
        existing.setStock(updatedProduct.getStock());

        return productService.createProduct(existing);
    }

    // ✅ DELETE PRODUCT
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Integer id) {
        productService.getProductById(id); // check existence
        // you can also create delete method in service, but for now:
        throw new RuntimeException("Delete logic not implemented yet");
    }
}

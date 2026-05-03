//package com.example.controller;
//import com.example.service.productservice;
//import com.example.entity.Product;
//import jakarta.validation.Valid;
//import org.springframework.data.domain.Page;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import java.util.List;
//@RestController
//@RequestMapping("/api/products")
//
//public class productcontroller {
//    private final productservice productService;
//
//    public productcontroller(productservice productService) {
//        this.productService = productService;
//    }
//
//
//    @GetMapping("/paged")
//    public Page<Product> getProducts(@RequestParam int page,
//                                      @RequestParam int size,
//                                      @RequestParam String sortBy) {
//        return productService.getProducts(page, size, sortBy);
//    }
//
//
//
//
//
//
//
//    @GetMapping("/available")
//    public List<String> getAvailableProducts() {
//        return productService.getAvailableProductNames();
//    }
//
//    @GetMapping("/price-above")
//    public List<Product> getProductsByPriceGreaterThan(@RequestParam Double price) {
//        return productService.getProductsByPriceGreaterThan(price);}
//    // ✅ CREATE PRODUCT
////    @PostMapping
////    public ResponseEntity<Product> createproduct(@Valid @RequestBody ProductRequest request) {
////        return productService.createProduct(product);
////    }
//
//    @PostMapping
//    public <ProductRequest> ResponseEntity<Product> createProduct(@Valid @RequestBody ProductRequest request) {
//        return ResponseEntity.ok(productService.createProduct((Product) request));
//    }
//
//    // ✅ GET PRODUCT BY ID
//    @GetMapping("/{id}")
//    public Product getProductById(@PathVariable Integer id) {
//        return productService.getProductById(id);
//    }
//
//    // ✅ GET ALL PRODUCTS
//    @GetMapping
//    public List<Product> getAllProducts() {
//        return productService.getAllProducts();
//    }
//
//    // ✅ UPDATE PRODUCT
//    @PutMapping("/{id}")
//    public Product updateProduct(@PathVariable Integer id,
//                                 @RequestBody Product updatedProduct) {
//
//        Product existing = productService.getProductById(id);
//
//        existing.setName(updatedProduct.getName());
//        existing.setPrice(updatedProduct.getPrice());
//        existing.setStock(updatedProduct.getStock());
//
//        return productService.createProduct(existing);
//    }
//
//    // ✅ DELETE PRODUCT
//    @DeleteMapping("/{id}")
//    public String deleteProduct(@PathVariable Integer id) {
//        productService.getProductById(id); // check existence
//        // you can also create delete method in service, but for now:
//        throw new RuntimeException("Delete logic not implemented yet");
//    }
//}
package com.example.controller;

import com.example.dto.productrequest;
import com.example.entity.Product;
import com.example.service.productservice;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Slf4j
public class productcontroller {

    private final productservice productService;

    public productcontroller(productservice productService) {
        this.productService = productService;
    }

    @GetMapping("/paged")
    public Page<Product> getProducts(@RequestParam int page,
                                     @RequestParam int size,
                                     @RequestParam String sortBy) {
        return productService.getProducts(page, size, sortBy);
    }

    @GetMapping("/available")
    public List<String> getAvailableProducts() {
        return productService.getAvailableProductNames();
    }

    @GetMapping("/price-above")
    public List<Product> getProductsByPriceGreaterThan(@RequestParam Double price) {
        return productService.getProductsByPriceGreaterThan(price);
    }

    // ✅ FIXED CREATE API
    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody productrequest request) {

        log.info("Creating product: {}", request.getName());

        return ResponseEntity.ok(productService.createProduct(request));
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Integer id) {
        return productService.getProductById(id);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Integer id,
                                 @RequestBody Product updatedProduct) {

        Product existing = productService.getProductById(id);

        existing.setName(updatedProduct.getName());
        existing.setPrice(updatedProduct.getPrice());
        existing.setStock(updatedProduct.getStock());

        return productService.createProduct(existing);
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Integer id) {
        throw new RuntimeException("Delete logic not implemented yet");
    }
}
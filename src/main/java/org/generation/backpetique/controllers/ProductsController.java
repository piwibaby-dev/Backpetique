package org.generation.backpetique.controllers;

import org.generation.backpetique.dtos.ProductsRequest;
import org.generation.backpetique.models.Products;
import org.generation.backpetique.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductsController {

    private final ProductsService productsService;

    @Autowired
    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping
    public ResponseEntity<List<Products>> getAllProducts() {
        return ResponseEntity.ok(productsService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Products> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productsService.getProductById(id));
    }

    @PostMapping
    public ResponseEntity<Products> createProduct(@RequestBody ProductsRequest request) {
        Products newProduct = productsService.createProduct(request);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Products> updateProduct(@PathVariable Long id, @RequestBody ProductsRequest request) {
        Products updatedProduct = productsService.updateProduct(id, request);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Products> deleteProduct(@PathVariable("productId") Long id) {
        Products deleteProduct = productsService.deleteProduct(id);
        return ResponseEntity.ok(deleteProduct);
    }
}

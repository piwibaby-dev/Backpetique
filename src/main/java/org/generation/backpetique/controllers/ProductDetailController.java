package org.generation.backpetique.controllers;

import org.generation.backpetique.dtos.ProductDetailRequest;
import org.generation.backpetique.dtos.ProductsRequest;
import org.generation.backpetique.models.ProductDetail;
import org.generation.backpetique.models.Products;
import org.generation.backpetique.services.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product_detail")
public class ProductDetailController {

    private final ProductDetailService productDetailService;

    @Autowired
    public ProductDetailController(ProductDetailService productDetailService){
        this.productDetailService = productDetailService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDetail>> getAllProductDetails() {
        return ResponseEntity.ok(productDetailService.getAllProductDetails());
    }

    @GetMapping("{orderId}/{productId}")
    public ResponseEntity<ProductDetail> getProuctDetailById(@PathVariable("orderId") Long orderId, @PathVariable("productId") Long productId) {
        return ResponseEntity.ok(productDetailService.getProuctDetailById(orderId,productId));
    }

    @PostMapping("{orderId}")
    public ResponseEntity<ProductDetail> createProduct(@PathVariable("orderId") Long orderId, @RequestBody ProductDetailRequest productDetailRequest) {
        ProductDetail newProductDetail = productDetailService.createProductDetail(orderId, productDetailRequest);
        return new ResponseEntity<>(newProductDetail, HttpStatus.CREATED);
    }

    @PutMapping("{orderId}/{productId}")
    public ResponseEntity<ProductDetail> updateProduct(@PathVariable("orderId") Long orderId, @PathVariable("productId") Long productId, @RequestParam String newState) {
        ProductDetail updatedProductDetail = productDetailService.updateState(orderId, productId,newState);
        return ResponseEntity.ok(updatedProductDetail);
    }
}

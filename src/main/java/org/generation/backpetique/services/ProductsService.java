package org.generation.backpetique.services;

import org.generation.backpetique.dtos.ProductsRequest;
import org.generation.backpetique.exceptions.ResourceNotFoundException;
import org.generation.backpetique.models.Brand;
import org.generation.backpetique.models.Category;
import org.generation.backpetique.models.Products;
import org.generation.backpetique.models.Users;
import org.generation.backpetique.repository.BrandRepository;
import org.generation.backpetique.repository.CategoryRepository;
import org.generation.backpetique.repository.PetsRepository;
import org.generation.backpetique.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;


    public ProductsService(ProductRepository productRepository, CategoryRepository categoryRepository, BrandRepository brandRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
    }

    public List<Products> getAllProducts() {
        return productRepository.findAll();
    }

    public Products getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con el id: " + id)
        );
    }

    public Products createProduct(ProductsRequest productsRequest) {
        Category category = categoryRepository.findById(productsRequest.categoryId()).orElseThrow(
                () -> new ResourceNotFoundException("Usuario con el id " + productsRequest.categoryId() + " no encontrado")
        );
        Brand brand = brandRepository.findById(productsRequest.brandId()).orElseThrow(
                () -> new ResourceNotFoundException("Usuario con el id " + productsRequest.brandId() + " no encontrado")
        );

        Products product = new Products();
        product.setSku(productsRequest.sku());
        product.setTitle(productsRequest.title());
        product.setPrice(productsRequest.price());
        product.setStock(productsRequest.stock());
        product.setPrescription(productsRequest.prescription());
        product.setWeight(productsRequest.weight());
        product.setUnit(productsRequest.unit());
        product.setUrlImg(productsRequest.urlImg());
        product.setCategoryId(category);
        product.setBrandId(brand);
        return productRepository.save(product);
    }

    public Products updateProduct(Long id, ProductsRequest productsRequest) {
        Products product = getProductById(id);
        if(productsRequest.sku() != null) product.setSku(productsRequest.sku());
        if(productsRequest.title() != null) product.setTitle(productsRequest.title());
        if(productsRequest.price() != null) product.setPrice(productsRequest.price());
        if(productsRequest.stock() != null) product.setStock(productsRequest.stock());
        if(productsRequest.prescription() != null) product.setPrescription(productsRequest.prescription());
        if(productsRequest.weight() != null) product.setWeight(productsRequest.weight());
        if(productsRequest.unit() != null) product.setUnit(productsRequest.unit());
        if(productsRequest.urlImg() != null) product.setUrlImg(productsRequest.urlImg());
        return productRepository.save(product);
    }

    public Products deleteProduct(Long id) {
        Products product = getProductById(id);
        productRepository.delete(product);
        return product;
    }
    
}
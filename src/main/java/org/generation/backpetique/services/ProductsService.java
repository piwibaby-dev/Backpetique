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
                () -> new ResourceNotFoundException("Categoria con el id " + productsRequest.categoryId() + " no encontrada")
        );
        Brand brand = brandRepository.findById(productsRequest.brandId()).orElseThrow(
                () -> new ResourceNotFoundException("Marca con el id " + productsRequest.brandId() + " no encontrada")
        );

        Products product = new Products();
        product.setSku(productsRequest.sku());
        product.setTitle(productsRequest.title());
        product.setDescription(productsRequest.description());
        product.setSpecies(productsRequest.species());
        product.setSubcategory(productsRequest.subcategory());
        product.setLifeStage(productsRequest.lifeStage());
        product.setBreedSize(productsRequest.breedSize());
        product.setPrice(productsRequest.price());
        if(productsRequest.stock() != null) product.setStock(productsRequest.stock());
        if(productsRequest.prescription() != null) product.setPrescription(productsRequest.prescription());
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
        if(productsRequest.description() != null) product.setDescription(productsRequest.description());
        if(productsRequest.species() != null) product.setSpecies(productsRequest.species());
        if(productsRequest.subcategory() != null) product.setSubcategory(productsRequest.subcategory());
        if(productsRequest.lifeStage() != null) product.setLifeStage(productsRequest.lifeStage());
        if(productsRequest.breedSize() != null) product.setBreedSize(productsRequest.breedSize());
        if(productsRequest.categoryId() != null) product.setCategoryId(categoryRepository.findById(productsRequest.categoryId()).orElseThrow(
                () -> new ResourceNotFoundException("Categoria con el id " + productsRequest.categoryId() + " no encontrada")
        ));
        if(productsRequest.brandId() != null) product.setBrandId(brandRepository.findById(productsRequest.brandId()).orElseThrow(
                () -> new ResourceNotFoundException("Marca con el id " + productsRequest.brandId() + " no encontrada")
        ));
        return productRepository.save(product);
    }

    public Products deleteProduct(Long id) {
        Products product = getProductById(id);
        productRepository.delete(product);
        return product;
    }
    
}
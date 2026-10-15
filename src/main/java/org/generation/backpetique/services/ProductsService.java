package org.generation.backpetique.services;

import org.generation.backpetique.dtos.ProductsRequest;
import org.generation.backpetique.exceptions.ResourceNotFoundException;
import org.generation.backpetique.models.Product;
import org.generation.backpetique.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con el id: " + id));
    }

    public Product createProduct(ProductsRequest request) {
        Product product = new Product();
        mapRequestToProduct(request, product);
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, ProductsRequest request) {
        Product product = getProductById(id);
        mapRequestToProduct(request, product);
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        productRepository.delete(product);
    }

    private void mapRequestToProduct(ProductsRequest request, Product product) {
        product.setSku(request.getSku());
        product.setTitle(request.getTitle());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setPrescription(request.getPrescription());
        product.setWeight(request.getWeight());
        product.setUnit(request.getUnit());
        product.setUrlImg(request.getUrlImg());
        product.setCategoryId(request.getCategoryId());
        product.setBrandId(request.getBrandId());
    }
}
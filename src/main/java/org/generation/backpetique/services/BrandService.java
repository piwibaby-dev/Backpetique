package org.generation.backpetique.services;

import org.generation.backpetique.dtos.BrandRequest;
import org.generation.backpetique.exceptions.ResourceNotFoundException;
import org.generation.backpetique.models.Brand;
import org.generation.backpetique.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class BrandService {

    private final BrandRepository brandRepository;


    @Autowired
    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }
    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }
    public Brand getBrandById(Long id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Marca no encontrada con id: " + id));
    }
    public Brand createBrand(BrandRequest request) {
        Brand brand = new Brand();
        brand.setBrandName(request.brandName());
        brand.setProductsId(request.productsId());
        brand.setCategoryId(request.categoryId());
        return brandRepository.save(brand);
    }
    public Brand updateBrand(Long id, BrandRequest request) {
        Brand existingBrand = getBrandById(id);
        existingBrand.setBrandName(request.brandName());
        existingBrand.setProductsId(request.productsId());
        existingBrand.setCategoryId(request.categoryId());
        return brandRepository.save(existingBrand);
    }
    public void deleteBrand(Long id) {
        Brand brand = getBrandById(id);
        brandRepository.delete(brand);
    }
}

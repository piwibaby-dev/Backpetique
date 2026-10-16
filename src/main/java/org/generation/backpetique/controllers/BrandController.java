package org.generation.backpetique.controllers;
import jakarta.validation.Valid;
import org.generation.backpetique.dtos.BrandRequest;
import org.generation.backpetique.models.Brand;
import org.generation.backpetique.services.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/api/brands")

public class BrandController {

    private final BrandService brandService;

    @Autowired
    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping
    public ResponseEntity<List<Brand>> getAllBrands() {
        List<Brand> brandList = brandService.getAllBrands();
        return ResponseEntity.ok(brandList);
    }

    @GetMapping("{id}")
    public ResponseEntity<Brand> getBrandById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(brandService.getBrandById(id));
    }

    @PostMapping
    public ResponseEntity<Brand> createBrand(@Valid @RequestBody BrandRequest brandRequest) {
        Brand createdBrand = brandService.createBrand(brandRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdBrand.getBrandId())
                .toUri();
        return ResponseEntity.created(location).body(createdBrand);
    }

    @PutMapping("{id}")
    public ResponseEntity<Brand> updateBrand(@PathVariable("id") Long id, @Valid
    @RequestBody BrandRequest brandRequest) {
        return ResponseEntity.ok(brandService.updateBrand(id, brandRequest));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteBrand(@PathVariable("id") Long id) {
        brandService.deleteBrand(id);
        return ResponseEntity.noContent().build();
    }


}

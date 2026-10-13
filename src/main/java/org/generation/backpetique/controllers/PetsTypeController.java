package org.generation.backpetique.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.generation.backpetique.dtos.PetsTypeRequest;
import org.generation.backpetique.models.PetsType;
import org.generation.backpetique.services.PetsTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/api/pet-types")
@AllArgsConstructor
public class PetsTypeController {

    private final PetsTypeService petsTypeService;

    @GetMapping
    public ResponseEntity<List<PetsType>> getAllPetsTypes() {
        return ResponseEntity.ok(petsTypeService.getAllPetsTypes());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<PetsType> getPetsTypeById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(petsTypeService.getPetsTypeById(id));
    }

    @PostMapping
    public ResponseEntity<PetsType> createPetsType(@Valid @RequestBody PetsTypeRequest request) {
        PetsType created = petsTypeService.createPetsType(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getPetTypeId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<PetsType> updatePetsType(@PathVariable("id") Long id, @Valid @RequestBody PetsTypeRequest request) {
        return ResponseEntity.ok(petsTypeService.updatePetsType(id, request));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<PetsType> deletePetsTypeById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(petsTypeService.deletePetsTypeById(id));
    }
}
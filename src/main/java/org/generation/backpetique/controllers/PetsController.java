package org.generation.backpetique.controllers;

import lombok.AllArgsConstructor;
import org.generation.backpetique.dtos.PetsRequest;
import org.generation.backpetique.models.Pets;
import org.generation.backpetique.services.PetsService;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping (path = "/api")
@AllArgsConstructor
public class PetsController {
    private final PetsService petsService;

    //primer endpoint
    @GetMapping(path = "/users/{userId}/pets")
    public ResponseEntity<List<Pets>> getAllByUserId(@PathVariable("userId") Long userId){
        List <Pets> pets = petsService.getAllByUserId(userId);
        return ResponseEntity.ok(pets);
    }

    //obtener un solo album (endpoint)
    @GetMapping(path = "pets/{petId}")
    public ResponseEntity<Pets> getPetById (@PathVariable("petId") Long petId){
        return ResponseEntity.ok(petsService.getPetById(petId));
    }

    @PostMapping(path = "/users/{userId}/pets")
    public ResponseEntity<Pets> createPetById (@PathVariable("userId") Long userId, @RequestBody PetsRequest petsRequest){
       Pets pet = petsService.createPetByUserId(userId,petsRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(pet.getId())
                .toUri();
        return ResponseEntity.created(location).body(pet);
    }

    //actualizar un album
    @PutMapping (path = "pets/{petId}")
    public ResponseEntity<Pets> updatePetById (@PathVariable ("petId") Long petId, @RequestBody PetsRequest petsRequest) {
        Pets pet = petsService.updatePetById(petId,petsRequest);
        return ResponseEntity.ok(pet);
    }

    //borrar un album
    @DeleteMapping (path = "pets/{petId}")
    public ResponseEntity<Pets> deletePetById(@PathVariable("petId") Long petId){
        Pets pet = petsService.deletePetById(petId);
        return ResponseEntity.ok(pet);
    }

}

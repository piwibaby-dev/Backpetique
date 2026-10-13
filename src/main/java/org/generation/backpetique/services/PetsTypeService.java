package org.generation.backpetique.services;

import lombok.AllArgsConstructor;
import org.generation.backpetique.dtos.PetsTypeRequest;
import org.generation.backpetique.exceptions.ResourceNotFoundException;
import org.generation.backpetique.models.PetsType;
import org.generation.backpetique.repository.PetsTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PetsTypeService {

    private final PetsTypeRepository petsTypeRepository;

    public List<PetsType> getAllPetsTypes() {
        return petsTypeRepository.findAll();
    }

    public PetsType getPetsTypeById(Long id) {
        return petsTypeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Tipo de mascota con el id " + id + " no encontrado")
        );
    }

    public PetsType createPetsType(PetsTypeRequest request) {
        PetsType petsType = new PetsType();
        petsType.setPetType(request.petType());
        return petsTypeRepository.save(petsType);
    }

    public PetsType updatePetsType(Long id, PetsTypeRequest request) {
        PetsType petsType = getPetsTypeById(id);
        if (request.petType() != null) {
            petsType.setPetType(request.petType());
        }
        return petsTypeRepository.save(petsType);
    }

    public PetsType deletePetsTypeById(Long id) {
        PetsType petsType = getPetsTypeById(id);
        petsTypeRepository.delete(petsType);
        return petsType;
    }
}
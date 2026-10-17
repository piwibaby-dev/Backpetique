package org.generation.backpetique.services;

import lombok.AllArgsConstructor;
import org.generation.backpetique.dtos.PetsRequest;
import org.generation.backpetique.exceptions.ResourceNotFoundException;
import org.generation.backpetique.models.Pets;
import org.generation.backpetique.models.PetsType;
import org.generation.backpetique.models.Users;
import org.generation.backpetique.repository.PetsRepository;
import org.generation.backpetique.repository.PetsTypeRepository;
import org.generation.backpetique.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PetsService {
    private PetsRepository petsRepository;
    private UsersRepository usersRepository;
    private PetsTypeRepository petsTypeRepository;

    public List<Pets> getAllByUserId (Long userId){
        if (!usersRepository.existsById(userId))throw new ResourceNotFoundException("Usuario con el id "+ userId+ " no encontrado");
        return petsRepository.findByUserId_Id(userId);
    }

    public Pets getPetById(Long petId){
        return petsRepository.findById(petId).orElseThrow(
                () -> new ResourceNotFoundException("Mascota con el id "+ petId +" no encontrado")
        );
    }

    public Pets createPetByUserId (Long userId, PetsRequest petRequest){
        Users user = usersRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("Usuario con el id "+ userId+ " no encontrado")
        );
        Pets pet = new Pets();
        pet.setPetName(petRequest.petName());
        pet.setPetAge(petRequest.petAge());
        pet.setPetSize(petRequest.petSize());
        pet.setPetWeight(petRequest.petWeight());
        pet.setUserId(user);

        if (petRequest.petTypeId() != null) {
            PetsType petsType = petsTypeRepository.findById(petRequest.petTypeId()).orElseThrow(
                    () -> new ResourceNotFoundException("Tipo de mascota con el id " + petRequest.petTypeId() + " no encontrado")
            );
            pet.setPetType(petsType);
        }
        petsRepository.save(pet);
        user.getPets().add(pet);
        usersRepository.save(user);

        return pet;
    }

    public Pets updatePetById (Long petId, PetsRequest petsRequest){
        Pets petStored = getPetById(petId);
        if (petsRequest.petName() != null) petStored.setPetName(petsRequest.petName());
        if (petsRequest.petAge() != null) petStored.setPetAge(petsRequest.petAge());
        if (petsRequest.petSize() != null) petStored.setPetSize(petsRequest.petSize());
        if (petsRequest.petWeight() != null) petStored.setPetWeight(petsRequest.petWeight());
        if (petsRequest.petTypeId() != null) {
            PetsType petsType = petsTypeRepository.findById(petsRequest.petTypeId()).orElseThrow(
                    () -> new ResourceNotFoundException("Tipo de mascota con el id " + petsRequest.petTypeId() + " no encontrado")
            );
            petStored.setPetType(petsType);
        }
        return petsRepository.save(petStored);
    }

    public  Pets deletePetById(Long petId){
        Pets pet = getPetById(petId);
        petsRepository.delete(pet);
        return pet;
    }

}

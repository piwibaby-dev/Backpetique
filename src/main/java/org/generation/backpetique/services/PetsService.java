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
    private PetsTypeRepository petsTypeRepository; // Se agrego para enlazar tipo de mascota con mascota

    public List<Pets> getAllByUserId (Long userId){
        if (!usersRepository.existsById(userId))throw new ResourceNotFoundException("Usuario con el id "+ userId+ " no encontrado");
        return petsRepository.findByUserId(userId);
    }

    public Pets getPetById(Long petId){
        return petsRepository.findById(petId).orElseThrow(
                () -> new ResourceNotFoundException("Mascota con el id "+ petId +" no encontrado")
        );
    }

    public Pets createPetByUserId (Long userId, PetsRequest petRequest){
        // Pasos a seguir
        // 1. Verificar que existe el artista y obtenerlo
        Users user = usersRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("Usuario con el id "+ userId+ " no encontrado")
        );
        // 2. creando objeto para mapear el dto
        Pets pet = new Pets();
        pet.setPetName(petRequest.petName());
        pet.setPetAge(petRequest.petAge());
        //COREGIDO: sobreescribia .setPerWeight en vez de escribirlo en .setPetSize
        pet.setPetSize(petRequest.petSize());
        pet.setPetWeight(petRequest.petWeigth());
        pet.setUserId(user);

      // 3. Buscar y asignar el PetsType ANTES de guardar
        if (petRequest.petTypeId() != null) {
            PetsType petsType = petsTypeRepository.findById(petRequest.petTypeId()).orElseThrow(
                    () -> new ResourceNotFoundException("Tipo de mascota con el id " + petRequest.petTypeId() + " no encontrado")
            );
            pet.setPetType(petsType);
        }
        //  guardarlo en album
        petsRepository.save(pet);
        // 4. actualizar la lista de albums del artista
        user.getPets().add(pet);
        usersRepository.save(user);

        return pet;
    }

    public Pets updatePetById (Long petId, PetsRequest petsRequest){
        Pets petStored = getPetById(petId);
        if (petsRequest.petName() != null) petStored.setPetName(petsRequest.petName());
        if (petsRequest.petAge() != null) petStored.setPetAge(petsRequest.petAge());
        if (petsRequest.petSize() != null) petStored.setPetSize(petsRequest.petSize());
        if (petsRequest.petWeigth() != null) petStored.setPetWeight(petsRequest.petWeigth());
        return petsRepository.save(petStored);
    }

    public  Pets deletePetById(Long petId){
        Pets pet = getPetById(petId);
        petsRepository.delete(pet);
        return pet;
    }

}

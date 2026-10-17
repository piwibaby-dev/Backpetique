package org.generation.backpetique.services;


import org.generation.backpetique.dtos.LoginRequest;
import org.generation.backpetique.dtos.UsersRequest;
import org.generation.backpetique.exceptions.ResourceNotFoundException;
import org.generation.backpetique.models.Users;
import org.generation.backpetique.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UsersService {

    private final UsersRepository usersRepository;

    @Autowired
    public UsersService(UsersRepository usersRepository) {this.usersRepository = usersRepository;}

    public List<Users> getAllUsers() {return usersRepository.findAll();}

    public Users getUsersById(Long id) {
        return usersRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Usuario con el id " + id + " no encontrado")
                );
    }

    public Users createUsers(UsersRequest newUsers) {
        if (usersRepository.existsByEmail(newUsers.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un usuario con el correo " + newUsers.email());
        }
        Users users = new Users();
        users.setUserName(newUsers.userName());
        users.setSurNames(newUsers.surNames());
        users.setPhoneNumber(newUsers.phoneNumber());
        users.setEmail(newUsers.email());
        users.setBirthDate(newUsers.birthDate());
        users.setPasswordSite(newUsers.passwordSite());
        return usersRepository.save(users);
    }

    public Users login(LoginRequest loginRequest) {
        return usersRepository.findByEmail(loginRequest.email())
                .filter(user -> user.getPasswordSite().equals(loginRequest.passwordSite()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Correo o contraseña incorrectos"));
    }

    public Users updateUsers(Long id, UsersRequest updateUsers) {
        Users oldUsers = usersRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Usuario con el id " + id + " no encontrado")
        );
        if (updateUsers.userName() != null) oldUsers.setUserName(updateUsers.userName());
        if (updateUsers.surNames() != null) oldUsers.setSurNames(updateUsers.surNames());
        if (updateUsers.phoneNumber() != null) oldUsers.setPhoneNumber(updateUsers.phoneNumber());
        if (updateUsers.email() != null && !updateUsers.email().equalsIgnoreCase(oldUsers.getEmail())
                && usersRepository.existsByEmail(updateUsers.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un usuario con el correo " + updateUsers.email());
        }
        if (updateUsers.email() != null) oldUsers.setEmail(updateUsers.email());
        if (updateUsers.birthDate() != null) oldUsers.setBirthDate(updateUsers.birthDate());
        if (updateUsers.passwordSite() != null) oldUsers.setPasswordSite(updateUsers.passwordSite());

        return usersRepository.save(oldUsers);
    }

    public Users deleteUsersById(Long id) {
        Users users = usersRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Usuario con el id " + id + " no encontrado")
        );
        usersRepository.delete(users);
        return users;
    }

}

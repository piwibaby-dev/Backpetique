package org.generation.backpetique.controllers;


import jakarta.validation.Valid;
import org.generation.backpetique.dtos.UsersRequest;
import org.generation.backpetique.models.Users;
import org.generation.backpetique.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/api/users")
public class UsersController {
    private final UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {this.usersService = usersService;}

    @GetMapping
    public ResponseEntity<List<Users>> getAllUsers(){
        List<Users> usersList = usersService.getAllUsers();
        return ResponseEntity.ok(usersList);
    }
    @GetMapping(path = "{usersId}")
    public ResponseEntity<Users> getUsersById(@PathVariable("usersId")Long id){
        Users user = usersService.getUsersById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<Users> createUsers(@Valid @RequestBody UsersRequest usersRequest) {
        Users users = usersService.createUsers(usersRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(users.getId())
                .toUri();
        return ResponseEntity.created(location).body(users);
    }

    @PutMapping(path = {"usersId"})
    public ResponseEntity<Users> updateUsersByID(@PathVariable("usersId")Long id,@RequestBody UsersRequest usersRequest) {
        Users users = usersService.updateUsers(id, usersRequest);
        return ResponseEntity.ok(users);
    }

    @DeleteMapping(path = {"usersId"})
    public ResponseEntity<Users> deleteUsersById(@PathVariable("usersId") Long id){
        Users users = usersService.deleteUsersById(id);
        return ResponseEntity.ok(users);
    }


}

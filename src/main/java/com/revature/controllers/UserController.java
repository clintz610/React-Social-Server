package com.revature.controllers;

import com.revature.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class UserController {
    @GetMapping
    public ResponseEntity<User> findAll() {
        User returnThis = new User();
        returnThis.setUsername("Poncho_Villa");
        return ResponseEntity.ok(returnThis);
    }

    @PostMapping
    public ResponseEntity<User> createUser(
            @RequestBody User neoUser
    ) {
        User returnThis = new User();
        returnThis.setUsername("User Created: "+ neoUser.getUsername());
        return ResponseEntity.ok(returnThis);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(
            @PathVariable(value = "id") Long userID
    ) {
        User returnThis = new User();
        returnThis.setUsername("Delete User: "+ userID);
        return ResponseEntity.ok(returnThis);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable(value = "id") Long userID,
            @RequestBody User neoUser
    ) {
        User returnThis = new User();
        returnThis.setUsername("Update User: "+ userID);
        return ResponseEntity.ok(returnThis);
    }

}

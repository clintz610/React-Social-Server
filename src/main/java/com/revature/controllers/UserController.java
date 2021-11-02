package com.revature.controllers;

import com.revature.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping(path="/testNoAuth")
    public ResponseEntity<User> getTestUserNoAuth() {
        User returnThis = new User();
        returnThis.setEmail("Poncho_Villa_No_Auth");
        return ResponseEntity.ok(returnThis);
    }

    @GetMapping(path="/testWithAuth")
    public ResponseEntity<User> getTestUserWithAuth() {
        User returnThis = new User();
        returnThis.setEmail("Poncho_Villa_With_Auth");
        return ResponseEntity.ok(returnThis);
    }

    @PostMapping
    public ResponseEntity<User> createUser(
            @RequestBody User neoUser
    ) {
        User returnThis = new User();
        returnThis.setFirstName("User Created: "+ neoUser.getFirstName());
        return ResponseEntity.ok(returnThis);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(
            @PathVariable(value = "id") Long userID
    ) {
        User returnThis = new User();
        returnThis.setFirstName("Delete User: "+ userID);
        return ResponseEntity.ok(returnThis);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable(value = "id") Long userID,
            @RequestBody User neoUser
    ) {
        User returnThis = new User();
        returnThis.setFirstName("Update User: "+ userID);
        return ResponseEntity.ok(returnThis);
    }

}

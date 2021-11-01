package com.revature.controllers;

import com.revature.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "https://localhost:3000")
@RequestMapping("/api/user")
public class UserController {

    @GetMapping
    public ResponseEntity<User> findAll() {
        User returnThis = new User();
        returnThis.setUsername("Poncho_Villa");
        return ResponseEntity.ok(returnThis);
    }

}

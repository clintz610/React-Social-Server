package com.revature.controllers;

import com.revature.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials="true")
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
}

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

    @GetMapping(path="/test")
    public ResponseEntity<User> getTestUser() {
        User returnThis = new User();
        returnThis.setUsername("Poncho_Villa");
        return ResponseEntity.ok(returnThis);
    }

}

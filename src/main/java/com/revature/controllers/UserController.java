package com.revature.controllers;

import com.revature.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.models.User;

@RestController
@RequestMapping("/api/user")
public class UserController {

    final private UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    /* Registers a new user, only requires a Token in the header, no body is required at the moment */

    @PostMapping(path = "/register")
    public ResponseEntity<String> registerUser(@AuthenticationPrincipal User user) {
        userService.registerUser(user);
        return ResponseEntity.ok("Successfully created user with email " + user.getEmail());
    }
    
}

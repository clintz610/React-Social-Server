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

    /* On login, checks if user exists in database after firebase has authenticated the user. If the user
    does not currently exist in the cloud database, the user is created and added to it. */

    @PostMapping(path = "/login")
    public ResponseEntity<String> loginUser(@AuthenticationPrincipal User user) {
        userService.loginUser(user);
        return ResponseEntity.ok("Successfully added user with email " + user.getEmail());
    }

    /* Registers a new user, only requires a Token in the header, no body is required at the moment */

    @PostMapping(path = "/register")
    public ResponseEntity<String> registerUser(@AuthenticationPrincipal User user) {
        userService.registerUser(user);
        return ResponseEntity.ok("Successfully created user with email " + user.getEmail());
    }

}

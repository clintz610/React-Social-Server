package com.revature.users;

import com.revature.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.users.User;

@RestController
@CrossOrigin(origins = {"http://reverb-ui-bucket.s3-website-us-west-1.amazonaws.com"})
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    //constructor
    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }


    /* On login, checks if user exists in database after firebase has authenticated the user. If the user
    does not currently exist in the cloud database, the user is created and added to it. */
    @PostMapping(path = "/login")
    public ResponseEntity<User> loginUser(@AuthenticationPrincipal User user) {
        userService.loginUser(user);
        return ResponseEntity.ok(user);
    }


    /* Registers a new user, only requires a Token in the header, no body is required at the moment */
    @PostMapping(path = "/register")
    public ResponseEntity<String> registerUser(@AuthenticationPrincipal User user) {
        userService.registerUser(user);
        return ResponseEntity.ok("Successfully created user with email " + user.getEmail());
    }




}

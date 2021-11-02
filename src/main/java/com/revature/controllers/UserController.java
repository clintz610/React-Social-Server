package com.revature.controllers;

import com.revature.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping(path="/testNoAuth")
    public ResponseEntity<User> getTestUserNoAuth(@AuthenticationPrincipal User user) {
        User returnThis = new User();
        returnThis.setEmail("Poncho_Villa_No_Auth");
        
        if(user != null) {
        	System.out.println("I figured out who I am! " + user.getUid());
        } else {
        	System.out.println("NO USER");
        }

        return ResponseEntity.ok(returnThis);
    }

    @GetMapping(path="/testWithAuth")
    public ResponseEntity<User> getTestUserWithAuth(@AuthenticationPrincipal User user) {
        User returnThis = new User();
        returnThis.setEmail("Poncho_Villa_With_Auth");
        
        System.out.println("I figured out who I am! " + user.getUid());
        
        return ResponseEntity.ok(returnThis);
    }
}

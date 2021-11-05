package com.revature.controllers;

import com.revature.services.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path = "api/like")
public class LikeController {
    private LikeService likeService;

    //constructor
    @Autowired
    public LikeController(LikeService likeService)
    {
        this.likeService = likeService;
    }

}

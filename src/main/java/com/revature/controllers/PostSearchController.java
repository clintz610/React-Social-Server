package com.revature.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.models.Post;
import com.revature.models.User;
import com.revature.services.PostService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path = "api/search")
public class PostSearchController {
	private final PostService postService;
	
	@Autowired
	public PostSearchController(PostService svc)
	{
		this.postService = svc;
	}

	/*
	 * Get all Posts from a specific user.
	 * User object in RequestBody
	 * returns ResponseEntity<List<Post>> */

    /*
	@GetMapping(path = "/user")
	public ResponseEntity<List<Post>> searchByUser(@RequestBody User user)
	{
		return ResponseEntity.ok(postService.getUserPosts(user.getUid()));
	}

     */
}

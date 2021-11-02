package com.revature.services;

import com.revature.models.Post;
import com.revature.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository)
    {
        this.postRepository = postRepository;
    }

    public List<Post> getPosts()
    {
        return postRepository.findAll();
    }

    public Post addNewPost(Post post)
    {
        postRepository.save(post);
        return post;
    }

    /*public List<Post> getUserPosts(String username)
    {

    }*/
}

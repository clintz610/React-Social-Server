package com.revature.services;

import com.revature.models.Post;
import com.revature.repositories.LikeRepository;
import com.revature.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeService {
    private PostRepository postRepository;
    private LikeRepository likeRepository;


    @Autowired
    public LikeService(PostRepository postRepository, LikeRepository likeRepository)
    {
        this.postRepository = postRepository;
        this.likeRepository = likeRepository;
    }

    public Integer getNumberofLikes(Long postId)
    {
       Optional<Post> post = postRepository.findById(postId);

       if(post.isPresent())
       {
           return likeRepository.getLikeByPost(post.get()).size();
       }

       throw new IllegalStateException("failed to get the likes"); //make custom exception later
    }
}

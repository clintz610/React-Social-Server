package com.revature.services;

import com.revature.models.Like;
import com.revature.models.Post;
import com.revature.models.User;
import com.revature.repositories.LikeRepository;
import com.revature.repositories.PostRepository;
import com.revature.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeService {
    private PostRepository postRepository;
    private LikeRepository likeRepository;
    private UserRepository userRepository;

    @Autowired
    public LikeService(PostRepository postRepository, LikeRepository likeRepository, UserRepository userRepository)
    {
        this.postRepository = postRepository;
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
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

    public void likePost(Long postId, User user)
    {
        Optional<Post> post = postRepository.findById(postId);


        if(post.isPresent())
        {
            if(likeRepository.getByPostAndUser(post.get(), user).isEmpty())
            {
                Like like = new Like();
                like.setPost(post.get());
                like.setUser(user);
                likeRepository.save(like);
            }
            else
                throw new IllegalStateException("If a user has liked the post already"); //create custom exception
        }
        else
            throw new IllegalStateException("post does not exist");
    }
}

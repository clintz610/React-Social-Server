package com.revature.likes;

import com.revature.exceptions.PostNotFoundException;
import com.revature.posts.Post;
import com.revature.users.User;
import com.revature.posts.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class LikeService {
    private PostRepository postRepository;
    private LikeRepository likeRepository;

    // constructor
    @Autowired
    public LikeService(PostRepository postRepository, LikeRepository likeRepository)
    {
        this.postRepository = postRepository;
        this.likeRepository = likeRepository;
    }

    /*  Parameter: postID
        Pulls number of likes on specific post from database
        Returns Integer
     */
    public Integer getNumberofLikes(UUID postId) throws PostNotFoundException
    {
       Optional<Post> post = postRepository.findById(postId);

       if(post.isPresent())
       {
           return likeRepository.getLikeByPost(post.get()).size();
       }

       throw new PostNotFoundException(); //make custom exception later
    }

    public void likePost(UUID postId, User user)
    {
        Optional<Post> post = postRepository.findById(postId);


        if(post.isPresent())
        {
            if(!checkIfAlreadyLiked(post.get(), user))
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

    public void unlikePost(UUID postId, User user) {
        Optional<Post> post = postRepository.findById(postId);

        if(post.isPresent()) {
            if(checkIfAlreadyLiked(post.get(), user)){
                Post query = postRepository.getById(postId);
                Like like = likeRepository.getByPostAndUser(query, user).get(0);
                likeRepository.delete(like);
            }
            else
                throw new IllegalStateException("User has not liked this post!");
        }
        else
            throw new IllegalStateException("post does not exist");

    }
    
    public boolean checkIfAlreadyLiked(UUID postId, User user) {
    	Optional<Post> post = postRepository.findById(postId);

        if(post.isPresent())
        {
        	return checkIfAlreadyLiked(post.get(), user);
        }
        else
            throw new IllegalStateException("post does not exist");
        
    }
    
    public boolean checkIfAlreadyLiked(Post post, User user) {
    	return !likeRepository.getByPostAndUser(post, user).isEmpty();
    }
}

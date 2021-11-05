package com.revature.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.revature.exceptions.UnauthorizedDeleteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.models.Comment;
import com.revature.models.Post;
import com.revature.models.Profile;
import com.revature.models.User;
import com.revature.repositories.CommentRepository;
import com.revature.repositories.PostRepository;
import com.revature.repositories.ProfileRepository;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ProfileRepository profileRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, PostRepository postRepository, ProfileRepository profileRepository)
    {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.profileRepository = profileRepository;
    }

    public List<Comment> getComments()
    {
        return commentRepository.findAll();
    }

    public Comment addNewComment(Comment comment, Long postId, User user)
    {
        Optional<Post> post = postRepository.findById(postId);

        if(post.isPresent())
        {
        	comment.setAuthor(user);
        	Optional<Profile> optProfile = profileRepository.getProfileByUser(user);
            Profile profile = null;
            if(!optProfile.isPresent()) {
            	profile = new Profile();
            	profile.setUser(user);
            	profileRepository.save(profile);
            	System.out.println("Generating default profile! (Probably should never happen...)");
            } else {
            	profile = optProfile.get();
            }
            comment.setProfile(profile);
            comment.setPost(post.get());
            post.get().setComments((Arrays.asList(comment)));
            commentRepository.save(comment);
            return comment;
        } else {
        	throw new IllegalStateException("This comment does not have an associated post.");
        }
    }

    public void deleteComment(Long commentId, User user) throws UnauthorizedDeleteException {
        Optional<Comment> temp = commentRepository.findById(commentId);

        if(temp.isPresent())
        {
            if(temp.get().getAuthor().equals(user))
            {
                commentRepository.delete(temp.get());
                System.out.println("comment deleted");
            }
            else
                throw new UnauthorizedDeleteException("Unauthorized user tired to delete a comment."); 
        }
        else
            throw new IllegalStateException("Comment does not exist");
    }
}

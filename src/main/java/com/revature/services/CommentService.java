package com.revature.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
        	Profile profile = profileRepository.getProfileByUser(user);
            comment.setProfile(profile);
            comment.setPost(post.get());
            post.get().setComments((Arrays.asList(comment)));
            commentRepository.save(comment);
            return comment;
        }

        throw new IllegalStateException("This comment does not have an associated post.");
    }

    public void deleteComment(Comment comment)
    {
        Optional<Comment> temp = commentRepository.findById(comment.getId());

        if(temp.isPresent())
        {
            commentRepository.deleteById(temp.get().getId());
            System.out.println("comment deleted");
        }
        else
            throw new IllegalStateException("Comment does not exist");
    }
}

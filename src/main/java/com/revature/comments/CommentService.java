package com.revature.comments;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.revature.exceptions.ProfileNotFoundException;
import com.revature.posts.Post;
import com.revature.users.User;
import com.revature.users.profiles.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.revature.posts.PostRepository;
import com.revature.users.profiles.ProfileRepository;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ProfileRepository profileRepository;

    // constructor
    @Autowired
    public CommentService(
            CommentRepository commentRepository,
            PostRepository postRepository,
            ProfileRepository profileRepository)
    {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.profileRepository = profileRepository;
    }
    /*  No parameters
        Returns all Comments in the database
     */
    public List<Comment> getComments()
    {
        return commentRepository.findAll();
    }

    /*  Parameters: Comment to add, postID, and user
        Adds a new comment to the database
        Returns the comment added to the database
     */

    public Comment addNewComment(Comment comment, UUID postId, User user) throws ProfileNotFoundException
    {
        Optional<Post> post = postRepository.findById(postId);

        if(post.isPresent())
        {
            // Set the author from the user
        	comment.setAuthor(user);

            // Set the time to current time in UTC to be offset in client to client time.
            comment.setDate(LocalDateTime.now(ZoneOffset.UTC));

            // Set the post ID
            comment.setPost(post.get());

            // Save the comment to the repository
            commentRepository.save(comment);

            // Return the completed comment as proof.
            return comment;
        } else {
        	throw new IllegalStateException("This comment does not have an associated post.");
        }
    }


}

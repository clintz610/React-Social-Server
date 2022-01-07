package com.revature.comments;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.revature.comments.dtos.NewCommentRequest;
import com.revature.exceptions.UserNotFoundException;
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

    public List<Comment> getCommentsByPost(Post post) {
        return commentRepository.getByPost(post);
    }

    /*  Parameters: Comment to add, postID, and user
        Adds a new comment to the database
        Returns the comment added to the database
     */

    public Comment addNewComment(NewCommentRequest comment, UUID postId, User user) throws UserNotFoundException
    {
        Optional<Post> post = postRepository.findById(postId);
        Comment returnComment = new Comment();

        if(post.isPresent())
        {
            // Set the author from the user
            returnComment.setAuthor(user);

            // Set the time to current time in UTC to be offset in client to client time.
            returnComment.setDate(LocalDateTime.now(ZoneOffset.UTC));

            // Set the post ID
            returnComment.setPost(post.get());

            // Set the text
            returnComment.setCommentText(comment.getCommentText());


            // Save the comment to the repository
            commentRepository.save(returnComment);

            // Return the completed comment as proof.
            return returnComment;
        } else {
        	throw new IllegalStateException("This comment does not have an associated post.");
        }
    }


}

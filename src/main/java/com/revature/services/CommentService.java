package com.revature.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import com.revature.exceptions.ProfileNotFoundException;
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
    /*
    public Comment addNewComment(Comment comment, Long postId, User user) throws ProfileNotFoundException
    {
        Optional<Post> post = postRepository.findById(postId);

        if(post.isPresent())
        {
        	comment.setAuthor(user);
        	Optional<Profile> optProfile = profileRepository.getProfileByUser(user);
            Profile profile = null;
            if(!optProfile.isPresent()) {
            	throw new ProfileNotFoundException();
            } else {
            	profile = optProfile.get();
            }
            Date date = new Date();
            DateFormat dform = new SimpleDateFormat("MMM d yyyy HH:mm z");
            dform.setTimeZone(TimeZone.getTimeZone("America/New_York"));
            String dateString = dform.format(date);
            comment.setDate(dateString);
            comment.setProfile(profile);
            comment.setPost(post.get());
            post.get().setComments((Arrays.asList(comment)));
            commentRepository.save(comment);
            return comment;
        } else {
        	throw new IllegalStateException("This comment does not have an associated post.");
        }
    }
     */

}

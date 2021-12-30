package com.revature.comments;

import java.util.List;

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

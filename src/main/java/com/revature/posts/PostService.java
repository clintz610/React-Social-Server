package com.revature.posts;

import com.revature.comments.Comment;
import com.revature.comments.dtos.AuthorDto;
import com.revature.comments.dtos.CommentRequest;
import com.revature.posts.dtos.NewPostRequest;
import com.revature.posts.dtos.PostResponse;
import com.revature.exceptions.UserNotFoundException;
import com.revature.posts.postmeta.PostMeta;
import com.revature.users.User;
import com.revature.comments.CommentRepository;
import com.revature.posts.postmeta.PostMetaRepository;
import com.revature.users.profiles.ProfileRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostService {
	private final PostRepository postRepository;
	private final CommentRepository commentRepository;
	private final ProfileRepository profileRepository;
	private final PostMetaRepository postMetaRepository;

	// constructor
	@Autowired
	public PostService(PostRepository postRepository, CommentRepository commentRepository,
			ProfileRepository profileRepository, PostMetaRepository postMetaRepository) {
		this.postRepository = postRepository;
		this.commentRepository = commentRepository;
		this.profileRepository = profileRepository;
		this.postMetaRepository = postMetaRepository;
	}

	/*  No parameters
		Returns all Post objects in database
	 */
	public List<PostResponse> getPosts() {
		List<Post> rawRepository = postRepository.findAll();
		List<PostResponse> refinedRepo = new LinkedList<>();
		for (int i = 0; i < rawRepository.size(); i++) {
			// Record the relevant data from the posts.
			Post rawPost = rawRepository.get(i);
			PostResponse refinedPost = new PostResponse(rawPost);

			// Get the post's comments
			List<Comment> rawComments = rawPost.getComments();
			List<CommentRequest> refinedComments = new LinkedList<>();
			for (int j = 0; j < rawComments.size(); j++){
				// Record the relevant data for a comment.
				Comment rawComment = rawComments.get(j);
				CommentRequest refinedComment = new CommentRequest();

				// Get the simple values
				refinedComment.setCommentId(rawComment.getId().toString());
				refinedComment.setCommentText(rawComment.getCommentText());
				refinedComment.setDate(rawComment.getDate());

				// Create the author object we need
				AuthorDto refinedAuthor = new AuthorDto(rawComment.getAuthor(), profileRepository);
				refinedComment.setAuthor(refinedAuthor);

				// Add the result to the list
				refinedComments.add(refinedComment);

			}
			refinedPost.setComments(refinedComments);

			refinedRepo.add(refinedPost);
		}

		return refinedRepo;
	}

	/*  Parameters: Post object, User object
		Adds a new Post to the database, registered to specific User
		Returns the Post added to the database
	 */

	public Post addNewPost(NewPostRequest post, User user) throws UserNotFoundException
    {
		// Create a post meta and a post
		PostMeta newPostMeta = new PostMeta();
		Post newPost = new Post();

		// Set the author
        newPostMeta.setAuthor(user);

		//post.setId(UUID.randomUUID());


		// Set the time of the post
        newPostMeta.setDate(LocalDateTime.now(ZoneOffset.UTC));

		// Set the content type
		newPostMeta.setContentType(post.getContentType());

		// Set the link
		if (post.getContentLink() != null) {
			newPost.setContentLink(post.getContentLink());
		}
		else {
			newPost.setContentLink(null);
		}

		// Set the content
		newPost.setPostText(post.getPostText());

		// Save the meta to the repository
		postMetaRepository.save(newPostMeta);
		newPost.setPostMeta(newPostMeta);

		// Save the new post and return the status
        return postRepository.save(newPost);
    }



	/*  Parameter:  User UID (from Firebase)
		Returns a list of all posts registered to the User
	 */

    /*
	public List<Post> getUserPosts(String authorUID) {
		List<Post> ret = new ArrayList<Post>();
		for (Post p : postRepository.findAll()) {
			if (p.getAuthor().getUid().equals(authorUID)) {
				ret.add(p);
			}
		}
		return ret;
	}

     */

}

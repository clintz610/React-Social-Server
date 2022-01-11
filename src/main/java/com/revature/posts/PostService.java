package com.revature.posts;

import com.revature.comments.Comment;
import com.revature.comments.dtos.AuthorDto;
import com.revature.comments.dtos.CommentRequest;
import com.revature.exceptions.GroupNotFoundException;
import com.revature.exceptions.UserNotInGroupException;
import com.revature.groups.Group;
import com.revature.groups.GroupRepository;
import com.revature.follow.FollowRepository;
import com.revature.posts.dtos.NewPostRequest;
import com.revature.posts.dtos.PostResponse;
import com.revature.exceptions.UserNotFoundException;
import com.revature.posts.postmeta.PostMeta;
import com.revature.users.User;
import com.revature.comments.CommentRepository;
import com.revature.posts.postmeta.PostMetaRepository;
import com.revature.users.UserRepository;
import com.revature.users.profiles.ProfileRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostService {
	private final FollowRepository followRepository;
	private final PostRepository postRepository;
	private final CommentRepository commentRepository;
	private final ProfileRepository profileRepository;
	private final PostMetaRepository postMetaRepository;
	private final UserRepository userRepository;
    private final GroupRepository groupRepository;

	// constructor
	@Autowired
	public PostService(PostRepository postRepository, CommentRepository commentRepository,
			ProfileRepository profileRepository, PostMetaRepository postMetaRepository, FollowRepository followRepository, GroupRepository groupRepository, UserRepository userRepository) {
		this.postRepository = postRepository;
		this.commentRepository = commentRepository;
		this.profileRepository = profileRepository;
		this.postMetaRepository = postMetaRepository;
        this.groupRepository = groupRepository;
		this.followRepository = followRepository;
		this.userRepository = userRepository;
	}

	/**  No parameters
		Returns all Post objects in database
	 */
	public List<PostResponse> getPosts() {
		List<Post> rawRepository = postRepository.findAll();

		return getComments(rawRepository);
	}

    public List<PostResponse> getGroupPosts(String groupName) {

        Group group = groupRepository.findGroupByName(groupName).orElseThrow(GroupNotFoundException::new);
        List<Post> actualPosts = postRepository.findPostsByGroupId(group);
        return getComments(actualPosts);

    }

	/**
	 * @param userId
	 * @return all post objects attached to a userId
	 */
	public List<PostResponse> getPostsOfUserId(String userId) {
		List<PostResponse> allPosts = getPosts();
		List<PostResponse> filteredPosts = new ArrayList<>();
		for(int i = 0; i < allPosts.size(); i++){
			if(allPosts.get(i).getAuthorID().equals(userId)){
				filteredPosts.add(allPosts.get(i));
			}
		}
		return filteredPosts;
	}

	public List<PostResponse> getPersonalPosts(String userId){
		//combined list of group posts, user posts, and following posts
        Set<PostResponse> personalPosts = new HashSet<>();

		//retrieve following posts
		List<PostResponse> followingPosts = getPostsOfFollowing(userId);
		//retrieve user posts
		List<PostResponse> userPosts = getPostsOfUserId(userId);
		//get the groups attached to a user
		User user = userRepository.getById(userId);
		List<Group> groups = user.getGroups();

		//find all posts in groups that user belongs to
		for(Group g : groups){
            personalPosts.addAll(getGroupPosts(g.getName()));
		}

        personalPosts.addAll(followingPosts);
        personalPosts.addAll(userPosts);

		//sorts post by date. .reversed() should sort newest to oldest

		List<PostResponse> list = personalPosts.stream()
				.sorted(Comparator.comparing(PostResponse::getDate))
				.collect(Collectors.toList());
        System.out.println(list);
        return list;
	}

	/**
	 * no parameters
	 * @returns all post objects attached to the userIds that the logged-in user is following
	 */
	public List<PostResponse> getPostsOfFollowing(String userId) {
		List<PostResponse> followingPosts = new ArrayList<>();

		//get all people following a given user and pull out user Ids.
		User followingUser = followRepository.findById(userId).get();
		List<User> following = followingUser.getFollowing();

		for(int i = 0; i < following.size(); i++){
			List<PostResponse> filteredPosts = getPostsOfUserId(following.get(i).getId());
			followingPosts.addAll(filteredPosts);
		}
		//System.out.println("right before followingPost returns");
		return followingPosts;
	}

	/**  Parameters: Post object, User object
		Adds a new Post to the database, registered to specific User
		Returns the Post added to the database
	 */

	public Post addNewPost(NewPostRequest post, User user) throws UserNotFoundException
    {
		// Create a post meta and a post
		PostMeta newPostMeta = new PostMeta();
		Post newPost = new Post();

		if (post.getGroupID() != null && !post.getGroupID().trim().equals("")) {
			newPostMeta.setGroup(groupRepository.findById(UUID.fromString(post.getGroupID())).get());

            List<User> users = newPostMeta.getGroup()
                                          .getUsers()
                                          .stream()
                                          .filter(e -> e.getEmail().equals(user.getEmail()))
                                          .collect(Collectors.toList());
            if(users.size() != 1) {
                throw new UserNotInGroupException();
            }
        }

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

	private List<PostResponse> getComments(List<Post> posts) {
		List<PostResponse> refinedRepo = new LinkedList<>();

		for (int i = 0; i < posts.size(); i++) {
			// Record the relevant data from the posts.
			Post rawPost = posts.get(i);
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

}

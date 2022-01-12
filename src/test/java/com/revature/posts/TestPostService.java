package com.revature.posts;

import com.revature.comments.Comment;
import com.revature.comments.CommentRepository;
import com.revature.common.util.ContentType;
import com.revature.exceptions.UserNotInGroupException;
import com.revature.follow.FollowRepository;
import com.revature.groups.Group;
import com.revature.groups.GroupRepository;
import com.revature.posts.dtos.NewPostRequest;
import com.revature.posts.dtos.PostResponse;
import com.revature.posts.postmeta.PostMeta;
import com.revature.posts.postmeta.PostMetaRepository;
import com.revature.users.User;
import com.revature.users.UserRepository;
import com.revature.users.profiles.Profile;
import com.revature.users.profiles.ProfileRepository;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.security.core.parameters.P;


import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;




public class TestPostService {

	private CommentRepository mockCommentRepository;
	private PostRepository mockPostRepository;
	private ProfileRepository mockProfileRepository;
	private PostMetaRepository mockPostMetaRepository;
	private FollowRepository mockFollowRepository;
	private UserRepository mockUserRepository ;
    private GroupRepository mockGroupRepository;

	private PostService postService;
	
	@BeforeEach
	public void Setup() 
	{
		mockCommentRepository = mock(CommentRepository.class);
		mockPostRepository = mock(PostRepository.class);
		mockProfileRepository = mock(ProfileRepository.class);
		mockPostMetaRepository = mock(PostMetaRepository.class);
		mockFollowRepository = mock(FollowRepository.class);
		mockUserRepository = mock(UserRepository.class);
		mockGroupRepository = mock(GroupRepository.class);

		postService = new PostService(mockPostRepository, mockCommentRepository, mockProfileRepository, mockPostMetaRepository, mockFollowRepository, mockGroupRepository, mockUserRepository);


	}

	@AfterEach
	public void cleanTestSetup(){
		postService = null;
	}

	// Get posts test
	@Test
	public void getPostsWithAnyRepo()
	{
		//Arrange
		when(mockPostRepository.findAll()).thenReturn(new ArrayList<Post>());
		//Act
		postService.getPosts();
		//Assert
		verify(mockPostRepository, times(1)).findAll();

	}

	@Test
	public void getPostWithOnePostWithComments(){

		//Arrange
		//Create Dummy User
		User testUser = new User();
		testUser.setEmail("Email@Rev.net");
		testUser.setId("0dVqG3mQr01tWwuIsghJMQm6oZKb");

		//Create dummy profile for MOCK call ew
		Profile testProfile = new Profile();
		testProfile.setId(UUID.randomUUID());
		testProfile.setFirstName("John");
		testProfile.setLastName("Smithy");
		testProfile.setProfileImg("Pictures are Hard");
		testProfile.setHeaderImg("Also hard in strings");
		testProfile.setAboutMe("Ignore this, its bad");
		testProfile.setBirthday("10/29/2069");
		testProfile.setHobby("Games Buddy");
		testProfile.setLocation("Narnia");
		testProfile.setUser(testUser);

		//Create Relevant post meta
		PostMeta testMeta = new PostMeta();
		testMeta.setId(UUID.randomUUID());
		testMeta.setDate(LocalDateTime.now());
		testMeta.setContentType(ContentType.VID);
		testMeta.setAuthor(testUser);

		//Create post and store meta on it.
		Post testPost = new Post();
		testPost.setId(UUID.randomUUID());
		testPost.setPostText("Derp");
		testPost.setContentLink("https://youtu.be/dQw4w9WgXcQ");
		testPost.setPostMeta(testMeta);

		//create comment and attach one way to the post.
		Comment testComment = new Comment();
		testComment.setCommentText("Roll Baby!");
		testComment.setAuthor(testUser);
		testComment.setDate(LocalDateTime.now());
		testComment.setId(UUID.randomUUID());
		testComment.setPost(testPost);

		//create list of comments, add test comment to the list, attach list of comments to post.
		List<Comment> testCommentsList = new ArrayList<>();
		testCommentsList.add(testComment);
		testPost.setComments(testCommentsList);

		//Create postlist, and put fully made post into it. (To be used as an argument for the getPosts() method
		List<Post> postList = new ArrayList<>();
		postList.add(testPost);

		//When you try to find all from the post repository, return test list.
		when(mockPostRepository.findAll()).thenReturn(postList);
		when(mockProfileRepository.getProfileByUser(testUser)).thenReturn(Optional.of(testProfile));


		//Act

		List<PostResponse> sutList = postService.getPosts();



		//Assert
		verify(mockPostRepository, times(1)).findAll();
		verify(mockProfileRepository, times(1)).getProfileByUser(any());
	}




	@Test
	public void getPostsOfUserIdWhenThereIsAMatchingField(){


		//Arrange

		//Create Dummy User
		User testUser = new User();
		testUser.setEmail("Email@Rev.net");
		testUser.setId("0dVqG3mQr01tWwuIsghJMQm6oZKb");;

		//Create Relevant post meta
		PostMeta testMeta = new PostMeta();
		testMeta.setId(UUID.randomUUID());
		testMeta.setDate(LocalDateTime.now());
		testMeta.setContentType(ContentType.VID);
		testMeta.setAuthor(testUser);

		//Create post to give to the post response
		Post testPost = new Post();
		testPost.setId(UUID.randomUUID());
		testPost.setPostText("Derp");
		testPost.setContentLink("https://youtu.be/dQw4w9WgXcQ");
		testPost.setPostMeta(testMeta);
		testPost.setComments(new ArrayList<Comment>());

		//Create a list and add post to it
		List<Post> testList = new ArrayList<>();
		testList.add(testPost);

		when(mockPostRepository.findAll()).thenReturn(testList);

		//Act
		List<PostResponse> sut = postService.getPostsOfUserId(testUser.getId());



		//Assert
		Assertions.assertTrue(sut.get(0).getId().equals(testPost.getId().toString()));
		verify(mockPostRepository, times(1)).findAll();



	}

	@Test
	public void getPostsOfUserIdWhenThereIsNotAMatchingField(){


		//Arrange

		//Create Dummy User
		User testUser = new User();
		testUser.setEmail("Email@Rev.net");
		testUser.setId("0dVqG3mQr01tWwuIsghJMQm6oZKb");;

		//Create Relevant post meta
		PostMeta testMeta = new PostMeta();
		testMeta.setId(UUID.randomUUID());
		testMeta.setDate(LocalDateTime.now());
		testMeta.setContentType(ContentType.VID);
		testMeta.setAuthor(testUser);

		//Create post to give to the post response
		Post testPost = new Post();
		testPost.setId(UUID.randomUUID());
		testPost.setPostText("Derp");
		testPost.setContentLink("https://youtu.be/dQw4w9WgXcQ");
		testPost.setPostMeta(testMeta);
		testPost.setComments(new ArrayList<Comment>());

		//Create a list and add post to it
		List<Post> testList = new ArrayList<>();
		testList.add(testPost);

		when(mockPostRepository.findAll()).thenReturn(testList);

		//Act
		List<PostResponse> sut = postService.getPostsOfUserId("Bad_Id");


		//Assert
		Assertions.assertTrue(sut.isEmpty());
		verify(mockPostRepository, times(1)).findAll();



	}

	@Test
	public void getPersonalPostsWhenOnePostInEachFeed() throws InterruptedException {

		//Arrange

		// Mock the group
		ArrayList<User> joinedUsers = new ArrayList<>();

		Group foundGroup = new Group();
		foundGroup.setName("Group");
		foundGroup.setDescription("I am Group");
		foundGroup.setProfilePic("Valid");
		foundGroup.setHeaderImg("Valid");
		foundGroup.setId(UUID.randomUUID());

		//List of Groups
		List<Group> groupList = new ArrayList<>();
		groupList.add(foundGroup);


		//Create Dummy FollowedUser
		User followedUser = new User();
		followedUser.setEmail("Email2@Rev.net");
		followedUser.setId("1dVqG3mQr01tWwuIsghJMQm6oZKb");
		followedUser.setGroups(groupList);
		joinedUsers.add(followedUser);

		//Create a list of users that personal user will follow
		List<User> followedUsers = new ArrayList<>();
		followedUsers.add(followedUser);

		//Create Dummy PersonalUser
		User personalUser = new User();
		personalUser.setEmail("Email@Rev.net");
		personalUser.setId("0dVqG3mQr01tWwuIsghJMQm6oZKb");
		personalUser.setGroups(groupList);
		joinedUsers.add(personalUser);
		personalUser.setFollowing(followedUsers);

		//Create a list of people following the followed user
		List<User> followingUsers = new ArrayList<>();
		followingUsers.add(personalUser);
		followedUser.setFollower(followingUsers);

		//Add both users to group.
		foundGroup.setUsers(joinedUsers);

		//Personal Post Section
		//Create Relevant post meta
		PostMeta personalMeta = new PostMeta();
		personalMeta.setId(UUID.randomUUID());
		personalMeta.setDate(LocalDateTime.now());
		personalMeta.setContentType(ContentType.VID);
		personalMeta.setAuthor(personalUser);

		//Create post to give to the post response
		Post personalPost = new Post();
		personalPost.setId(UUID.randomUUID());
		personalPost.setPostText("Personal");
		personalPost.setContentLink("https://youtu.be/dQw4w9WgXcQ");
		personalPost.setPostMeta(personalMeta);
		personalPost.setComments(new ArrayList<Comment>());

		Thread.sleep(5); //Used to fake timestamp differnces
		//Following Post
		//Create Relevant post meta
		PostMeta followedMeta = new PostMeta();
		followedMeta.setId(UUID.randomUUID());
		followedMeta.setDate(LocalDateTime.now());
		followedMeta.setContentType(ContentType.VID);
		followedMeta.setAuthor(followedUser);

		//Create post to give to the post response
		Post followedPost = new Post();
		followedPost.setId(UUID.randomUUID());
		followedPost.setPostText("Followed");
		followedPost.setContentLink("https://youtu.be/dQw4w9WgXcQ");
		followedPost.setPostMeta(followedMeta);
		followedPost.setComments(new ArrayList<Comment>());

		Thread.sleep(5); //Used to fake timestamp differnces
		//Group Post
		//Create Relevant post meta
		PostMeta groupMeta = new PostMeta();
		groupMeta.setId(UUID.randomUUID());
		groupMeta.setDate(LocalDateTime.now());
		groupMeta.setContentType(ContentType.VID);
		groupMeta.setAuthor(followedUser);
		groupMeta.setGroup(foundGroup);

		//Create post to give to the post response
		Post groupPost = new Post();
		groupPost.setId(UUID.randomUUID());
		groupPost.setPostText("Group");
		groupPost.setContentLink("https://youtu.be/dQw4w9WgXcQ");
		groupPost.setPostMeta(groupMeta);
		groupPost.setComments(new ArrayList<Comment>());

		//Create a list and add post to it
		List<Post> testList = new ArrayList<>();
		testList.add(personalPost);
		testList.add(groupPost);
		testList.add(followedPost);

		//Create a sorted list for verification in assert. Scales with more objects.
		List<PostResponse> sortedList = testList.stream()
				.map(s -> new PostResponse(s))
				.collect(Collectors.toList());

		sortedList = sortedList.stream()
				.sorted(Comparator.comparing(PostResponse::getDate))
				.collect(Collectors.toList());

		//create a list that has only group posts
		List<Post> groupPostList = new ArrayList<>();
		groupPostList.add(groupPost);

		//When each repository is called, return the built data above instead of actually hitting the DB.
		//---------//
		when(mockFollowRepository.findById(any())).thenReturn(Optional.of(personalUser));
		when(mockPostRepository.findAll()).thenReturn(testList);
		when(mockUserRepository.getById(any())).thenReturn((personalUser));
		when(mockGroupRepository.findGroupByName(foundGroup.getName())).thenReturn(Optional.of(foundGroup));
		when(mockPostRepository.findPostsByGroupId(foundGroup)).thenReturn(groupPostList);

		//Act
		List<PostResponse> sut = postService.getPersonalPosts(personalUser.getId());


		//Assert
		for (int counter = 0; counter < sut.size(); counter++) {
			Assertions.assertTrue(sut.get(counter).getPostText().equals(sortedList.get(counter).getPostText()));
		}

		verify(mockFollowRepository, times(1)).findById(any());


	}






	// Get group posts tests
	@Test
	public void getGroupPosts(){
		//Arrange

		// Mock the group
		ArrayList<User> joinedUsers = new ArrayList<>();

		Group foundGroup = new Group();
		foundGroup.setName("Group");
		foundGroup.setDescription("I am Group");
		foundGroup.setProfilePic("Valid");
		foundGroup.setHeaderImg("Valid");
		foundGroup.setUsers(joinedUsers);
		foundGroup.setId(UUID.randomUUID());

		when(mockGroupRepository.findGroupByName(foundGroup.getName())).thenReturn(Optional.of(foundGroup));
		when(mockPostRepository.findPostsByGroupId(foundGroup)).thenReturn(new ArrayList<Post>());

		//Act
		postService.getGroupPosts(foundGroup.getName());


		//Assert
		verify(mockGroupRepository, times(1)).findGroupByName(foundGroup.getName());
		verify(mockPostRepository, times(1)).findPostsByGroupId(foundGroup);
	}

	// Add post tests

	@Test
	public void addNewPostWithNoLinkNoGroup()
	{
		// Arrange
		// Create a mock of our DTO and our User
		NewPostRequest post = new NewPostRequest();
		User user = new User();
		user.setEmail("test@test.com");
		user.setId("0dVqG3mQr01tWwuIsghJMQm6oZKb");
		post.setPostText("Test text");

		// Whenever we run the addNewPost, we need to catch the value of the Post that results from it
		ArgumentCaptor<Post> targetCatch = ArgumentCaptor.forClass(Post.class);
		when(mockPostRepository.save(targetCatch.capture())).thenAnswer(
				(InvocationOnMock invocation) -> targetCatch.getValue()
		);

		// Grab the meta (so we don't save to the repo)
		ArgumentCaptor<PostMeta> metaCatch = ArgumentCaptor.forClass(PostMeta.class);
		when(mockPostMetaRepository.save(metaCatch.capture())).thenAnswer(
				(InvocationOnMock invocation) -> metaCatch.getValue()
		);


		// Act
		postService.addNewPost(post, user);


		// Assert
		Assertions.assertTrue(targetCatch.getValue().getPostMeta().getAuthor().equals(user));
		verify(mockPostRepository, times(1)).save(targetCatch.getValue());
	}

	@Test
	public void addNewPostWithValidGroup()
	{
		// Arrange

		// Mock the user
		User user = new User();
		user.setEmail("test@test.com");
		user.setId("0dVqG3mQr01tWwuIsghJMQm6oZKb");

		// Mock the group
		ArrayList<User> joinedUsers = new ArrayList<>();
		joinedUsers.add(user);


		Group foundGroup = new Group();
		foundGroup.setName("Group");
		foundGroup.setDescription("I am Group");
		foundGroup.setProfilePic("Valid");
		foundGroup.setHeaderImg("Valid");
		foundGroup.setUsers(joinedUsers);
		foundGroup.setId(UUID.randomUUID());

		// Create a mock of our DTO
		NewPostRequest post = new NewPostRequest();
		post.setGroupID(foundGroup.getId().toString());
		post.setPostText("Test text");



		when(mockGroupRepository.findById(foundGroup.getId())).thenReturn(Optional.of(foundGroup));

		// Grabs the post
		ArgumentCaptor<Post> targetCatch = ArgumentCaptor.forClass(Post.class);
		when(mockPostRepository.save(targetCatch.capture())).thenAnswer(
				(InvocationOnMock invocation) -> targetCatch.getValue()
		);

		// Grab the meta (so we don't save to the repo)
		ArgumentCaptor<PostMeta> metaCatch = ArgumentCaptor.forClass(PostMeta.class);
		when(mockPostMetaRepository.save(metaCatch.capture())).thenAnswer(
				(InvocationOnMock invocation) -> metaCatch.getValue()
		);

		// Act
		postService.addNewPost(post, user);

		// Assert
		verify(mockGroupRepository, times(1)).findById(foundGroup.getId());

		Assertions.assertTrue(targetCatch.getValue().getPostMeta().getGroup().getId().equals(foundGroup.getId()));
		Assertions.assertTrue(metaCatch.getValue().getAuthor().getId().equals(user.getId()));


	}

	@Test
	public void saveValidUserWithContentLink()
	{
		// Arrange
		// Create a mock of our DTO and our User
		User user = new User();
		user.setEmail("test@test.com");
		user.setId("0dVqG3mQr01tWwuIsghJMQm6oZKb");

		NewPostRequest post = new NewPostRequest();
		post.setPostText("Test text");
		post.setContentType(ContentType.VID);
		post.setContentLink("https://youtu.be/dQw4w9WgXcQ");

		// Whenever we run the addNewPost, we need to catch the value of the Post that results from it
		ArgumentCaptor<Post> targetCatch = ArgumentCaptor.forClass(Post.class);
		when(mockPostRepository.save(targetCatch.capture())).thenAnswer(
				(InvocationOnMock invocation) -> targetCatch.getValue()
		);

		// Grab the meta (so we don't save to the repo)
		ArgumentCaptor<PostMeta> metaCatch = ArgumentCaptor.forClass(PostMeta.class);
		when(mockPostMetaRepository.save(metaCatch.capture())).thenAnswer(
				(InvocationOnMock invocation) -> metaCatch.getValue()
		);


		// Act
		postService.addNewPost(post, user);


		// Assert
		Assertions.assertTrue(targetCatch.getValue().getContentLink().equals(post.getContentLink()));
		Assertions.assertTrue(metaCatch.getValue().getAuthor().getId().equals(user.getId()));
		verify(mockPostRepository, times(1)).save(targetCatch.getValue());

	}


	@Test
	public void throwsNotInGroupWhenSavingInvalid()
	{
		// Arrange

		// Mock the user
		User user = new User();
		user.setEmail("test@test.com");
		user.setId("0dVqG3mQr01tWwuIsghJMQm6oZKb");

		// Mock the group
		ArrayList<User> joinedUsers = new ArrayList<>();


		Group foundGroup = new Group();
		foundGroup.setName("Group");
		foundGroup.setDescription("I am Group");
		foundGroup.setProfilePic("Valid");
		foundGroup.setHeaderImg("Valid");
		foundGroup.setUsers(joinedUsers);
		foundGroup.setId(UUID.randomUUID());

		// Create a mock of our DTO
		NewPostRequest post = new NewPostRequest();
		post.setGroupID(foundGroup.getId().toString());
		post.setPostText("Test text");



		when(mockGroupRepository.findById(foundGroup.getId())).thenReturn(Optional.of(foundGroup));

		// Grabs the post
		ArgumentCaptor<Post> targetCatch = ArgumentCaptor.forClass(Post.class);
		when(mockPostRepository.save(targetCatch.capture())).thenAnswer(
				(InvocationOnMock invocation) -> targetCatch.getValue()
		);

		// Grab the meta (so we don't save to the repo)
		ArgumentCaptor<PostMeta> metaCatch = ArgumentCaptor.forClass(PostMeta.class);
		when(mockPostMetaRepository.save(metaCatch.capture())).thenAnswer(
				(InvocationOnMock invocation) -> metaCatch.getValue()
		);

		// Act
		Assertions.assertThrows(
				UserNotInGroupException.class,
				() -> postService.addNewPost(post, user),
				"Expected User Not In Group Exception to be thrown when User not in Group."
		);

		// Assert
		verify(mockGroupRepository, times(1)).findById(foundGroup.getId());
		verify(mockPostRepository, times(0)).save(any());

	}

	/*
	@Test
	public void addNewPostNegative()
	{
		NewPostRequest post = new NewPostRequest();
		User user = new User();
		Profile profile = new Profile();
		Mockito.when(mockProfileRepository.getProfileByUser(user)).thenReturn(Optional.empty());
		try {
			postService.addNewPost(post, user);
			fail();
		}
		catch(Exception e)
		{
			assertEquals(e.getClass(), ProfileNotFoundException.class);
		}
	}
	*/




	
	
}

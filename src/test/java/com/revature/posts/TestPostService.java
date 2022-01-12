package com.revature.posts;

import com.revature.comments.CommentRepository;
import com.revature.common.util.ContentType;
import com.revature.exceptions.UserNotInGroupException;
import com.revature.follow.FollowRepository;
import com.revature.groups.Group;
import com.revature.groups.GroupRepository;
import com.revature.posts.dtos.NewPostRequest;
import com.revature.posts.postmeta.PostMeta;
import com.revature.posts.postmeta.PostMetaRepository;
import com.revature.users.User;
import com.revature.users.UserRepository;
import com.revature.users.profiles.ProfileRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

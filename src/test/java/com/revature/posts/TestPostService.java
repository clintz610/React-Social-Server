package com.revature.posts;

import com.revature.comments.CommentRepository;
import com.revature.follow.FollowRepository;
import com.revature.groups.Group;
import com.revature.groups.GroupRepository;
import com.revature.posts.dtos.NewPostRequest;
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

import static org.mockito.Mockito.*;




public class TestPostService {

	private CommentRepository mockCommentRepository;
	private PostRepository mockPostRepository;
	private ProfileRepository mockProfileRepository;
	private PostMetaRepository mockPostMetaRepositroy;
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
		mockPostMetaRepositroy = mock(PostMetaRepository.class);
		mockFollowRepository = mock(FollowRepository.class);
		mockUserRepository = mock(UserRepository.class);
		mockGroupRepository = mock(GroupRepository.class);

		postService = new PostService(mockPostRepository, mockCommentRepository, mockProfileRepository, mockPostMetaRepositroy, mockFollowRepository, mockGroupRepository, mockUserRepository);


	}

	@AfterEach
	public void cleanTestSetup(){
		postService = null;
	}

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

		// Mock the group
		ArrayList<User> joinedUsers = new ArrayList<>();


		Group foundGroup = new Group();
		foundGroup.setName("Group");
		foundGroup.setDescription("I am Group");
		foundGroup.setProfilePic("Valid");
		foundGroup.setHeaderImg("Valid");
		foundGroup.setUsers(joinedUsers);

		// Create a mock of our DTO and our User
		NewPostRequest post = new NewPostRequest();
		User user = new User();
		user.setEmail("test@test.com");
		user.setId("0dVqG3mQr01tWwuIsghJMQm6oZKb");
		post.setPostText("Test text");




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

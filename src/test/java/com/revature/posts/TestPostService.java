package com.revature.posts;

import com.revature.ReverbApplication;
import com.revature.comments.CommentRepository;
import com.revature.exceptions.ProfileNotFoundException;
import com.revature.follow.FollowRepository;
import com.revature.groups.GroupRepository;
import com.revature.posts.dtos.NewPostRequest;
import com.revature.posts.postmeta.PostMetaRepository;
import com.revature.users.User;
import com.revature.users.UserRepository;
import com.revature.users.profiles.Profile;
import com.revature.users.profiles.ProfileRepository;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;


import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.*;
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
		mockCommentRepository = Mockito.mock(CommentRepository.class);
		mockPostRepository = Mockito.mock(PostRepository.class);
		mockProfileRepository = Mockito.mock(ProfileRepository.class);
		mockPostMetaRepositroy = Mockito.mock(PostMetaRepository.class);
		mockFollowRepository = Mockito.mock(FollowRepository.class);
		mockUserRepository = Mockito.mock(UserRepository.class);
		mockGroupRepository = Mockito.mock(GroupRepository.class);

		postService = new PostService(mockPostRepository, mockCommentRepository, mockProfileRepository, mockPostMetaRepositroy, mockFollowRepository, mockGroupRepository, mockUserRepository);


	}

	@AfterEach
	public void cleanTestSetup(){
		postService = null;
	}

	@Test
	public void addNewPostWithNoLink()
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
		Mockito.when(mockPostRepository.save(targetCatch.capture())).thenAnswer(
				(InvocationOnMock invocation) -> targetCatch.getValue()
		);



		// Act
		postService.addNewPost(post, user);


		// Assert
		Assertions.assertTrue(targetCatch.getValue().getPostMeta().getAuthor().equals(user));
		verify(mockPostRepository, times(1)).save(targetCatch.getValue());
	}


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




	
	
}

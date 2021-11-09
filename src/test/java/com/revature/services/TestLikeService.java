package com.revature.services;

import com.revature.models.Like;
import com.revature.models.Post;
import com.revature.models.User;
import com.revature.repositories.LikeRepository;
import com.revature.repositories.PostRepository;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.revature.ReverbApplication;
import com.revature.exceptions.PostNotFoundException;

@SpringBootTest(classes = ReverbApplication.class)
@RunWith(SpringRunner.class)
// @ActiveProfiles("test")
public class TestLikeService {

	private LikeRepository likeRepository;

	private PostRepository postRepository;

	@BeforeEach
	public void Setup() {
		//mocks the repositories for each test
		postRepository = Mockito.mock(PostRepository.class);
		likeRepository = Mockito.mock(LikeRepository.class);

	
	}
	
	@Test
	public void getNumberofLikesPositive() throws Exception{
		//tests getNumberofLikes for no likes on a new post
		Post post = new Post();

		Mockito.when(postRepository.findById(8L)).thenReturn(Optional.of(post));
		Mockito.when(likeRepository.getLikeByPost(post)).thenReturn(new ArrayList<Like>());

		LikeService likeService = new LikeService(postRepository, likeRepository);

		assertThat(likeService.getNumberofLikes(8L)).isEqualTo(0);
	}

	@Test
	public void getNumberofLikesNegative() {
		//tests the post does not exist exception
		Post post = new Post();
		
		Mockito.when(postRepository.findById(8L)).thenReturn(Optional.empty());
		Mockito.when(likeRepository.getLikeByPost(post)).thenReturn(new ArrayList<Like>());
		LikeService likeService = new LikeService(postRepository, likeRepository);
		try {
			likeService.getNumberofLikes(8L);
			fail();
		} catch (Exception e) {
			assertEquals(e.getClass(), PostNotFoundException.class);
		}	
	}

	@Test
	public void likePostTest() 
	{
		Post post = new Post();
		User user = new User();

		Mockito.when(postRepository.findById(8L)).thenReturn(Optional.empty());
		LikeService likeService = new LikeService(postRepository, likeRepository);
		likeService.likePost(post.getId(), user);

		assertTrue(likeService.checkIfAlreadyLiked(post.getId(), user));
	}

	@Test
	public void likeTest() 
	{
		Post post = new Post();
		User user = new User();

		Mockito.when(postRepository.findById(8L)).thenReturn(Optional.empty());
		LikeService likeService = new LikeService(postRepository, likeRepository);
		likeService.likePost(post.getId(), user);

		assertTrue(likeService.checkIfAlreadyLiked(post, user));
	}

}

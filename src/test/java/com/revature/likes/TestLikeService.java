package com.revature.likes;

import com.revature.likes.Like;
import com.revature.likes.LikeService;
import com.revature.posts.Post;
import com.revature.users.User;
import com.revature.likes.LikeRepository;
import com.revature.posts.PostRepository;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import com.revature.ReverbApplication;
import com.revature.exceptions.PostNotFoundException;

@SpringBootTest(classes = ReverbApplication.class)
public class TestLikeService {

	private LikeRepository likeRepository;

	private PostRepository postRepository;

	@BeforeEach
	public void setup() {
		//mocks the repositories for each test
		postRepository = Mockito.mock(PostRepository.class);
		likeRepository = Mockito.mock(LikeRepository.class);

	
	}

	/*
	@Test
	public void getNumberofLikesPositive() throws PostNotFoundException {
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
	public void likePost() 
	{
		Post post = new Post();
		User user = new User();
		Like like = new Like();//for some reason the all args constructor doesn't work
		List<Like> array = new ArrayList<Like>();
		Mockito.when(postRepository.findById(8L)).thenReturn(Optional.of(post));
		Mockito.when(likeRepository.getByPostAndUser(post,user)).thenReturn(array);
		LikeService likeService = new LikeService(postRepository, likeRepository);
		likeService.likePost(8L, user);
		array.add(like);
		assertTrue(likeService.checkIfAlreadyLiked(post, user));
	}
	 */

}

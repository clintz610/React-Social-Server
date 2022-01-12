package com.revature.likes;

import com.revature.posts.Post;
import com.revature.users.User;
import com.revature.posts.PostRepository;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import com.revature.ReverbApplication;
import com.revature.exceptions.PostNotFoundException;

@SpringBootTest(classes = ReverbApplication.class)
class TestLikeService {

	private LikeRepository likeRepository;
	private PostRepository postRepository;

	@BeforeEach
	public void setup() {
		//mocks the repositories for each test
		postRepository = Mockito.mock(PostRepository.class);
		likeRepository = Mockito.mock(LikeRepository.class);
	}

	@Test
	void getNumberOfLikesPositive() throws PostNotFoundException {
		//tests getNumberofLikes for no likes on a new post
		Post post = new Post();
		UUID uuid = UUID.randomUUID();
		Mockito.when(postRepository.findById(uuid)).thenReturn(Optional.of(post));
		Mockito.when(likeRepository.getLikeByPost(post)).thenReturn(new ArrayList<Like>());

		LikeService likeService = new LikeService(postRepository, likeRepository);

		assertThat(likeService.getNumberofLikes(uuid)).isZero();

	}


	@Test
	void getNumberofLikesNegative() {
		//tests the post does not exist exception
		Post post = new Post();
		UUID uuid = UUID.randomUUID();
		Mockito.when(postRepository.findById(uuid)).thenReturn(Optional.empty());
		Mockito.when(likeRepository.getLikeByPost(post)).thenReturn(new ArrayList<Like>());
		LikeService likeService = new LikeService(postRepository, likeRepository);
		try {
			likeService.getNumberofLikes(uuid);
			Assertions.fail();
		} catch (Exception e) {
			Assertions.assertEquals(e.getClass(), PostNotFoundException.class);
		}
	}

	@Test
	void likePost()
	{
		Post post = new Post();
		User user = new User();
		Like like = new Like();//for some reason the all args constructor doesn't work
		List<Like> array = new ArrayList<Like>();
		UUID uuid = UUID.randomUUID();
		Mockito.when(postRepository.findById(uuid)).thenReturn(Optional.of(post));
		Mockito.when(likeRepository.getByPostAndUser(post,user)).thenReturn(array);
		LikeService likeService = new LikeService(postRepository, likeRepository);
		likeService.likePost(uuid, user);
		array.add(like);
		assertTrue(likeService.checkIfAlreadyLiked(post, user));
	}

	@Test
	void checkIfAlreadyLiked_valid()
	{
		Post post = new Post();
		User user = new User();
		Like like = new Like();//for some reason the all args constructor doesn't work
		List<Like> array = new ArrayList<Like>();
		UUID uuid = UUID.randomUUID();
		post.setId(uuid);
		Mockito.when(postRepository.findById(uuid)).thenReturn(Optional.of(post));
		Mockito.when(likeRepository.getByPostAndUser(post,user)).thenReturn(array);
		LikeService likeService = new LikeService(postRepository, likeRepository);
		likeService.likePost(uuid, user);
		array.add(like);
		assertTrue(likeService.checkIfAlreadyLiked(post.getId(), user));
	}

	@Test
	void checkIfAlreadyLiked_invalid(){
		LikeService likeService = new LikeService(postRepository, likeRepository);
		UUID uuid = UUID.randomUUID();
		IllegalStateException thrown = assertThrows(
				IllegalStateException.class,
				() -> likeService.checkIfAlreadyLiked(uuid, null),
				"Expected IllegalStateException to throw, but it didn't");
		assertTrue(thrown.getMessage().contains("post does not exist"));
	}

	@Test
	void unlikePost_valid(){
		Post post = new Post();
		User user = new User();
		Like like = new Like();//for some reason the all args constructor doesn't work
		List<Like> array = new ArrayList<Like>();
		UUID uuid = UUID.randomUUID();
		post.setId(uuid);
		uuid = UUID.randomUUID();
		user.setId(uuid.toString());
		Optional<Post> oPost = Optional.of(post);
		array.add(like);
		LikeService likeService = new LikeService(postRepository, likeRepository);

		Mockito.when(postRepository.findById(any())).thenReturn(oPost);
		Mockito.when(postRepository.getById(any())).thenReturn(post);
		Mockito.when(likeRepository.getByPostAndUser(post, user)).thenReturn(array);
		Mockito.doNothing().when(likeRepository).delete(like);

		likeService.unlikePost(post.getId(), user);

		Mockito.verify(likeRepository, Mockito.times(1)).delete(like);
	}
}

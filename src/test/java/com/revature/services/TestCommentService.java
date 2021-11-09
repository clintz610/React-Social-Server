package com.revature.services;

import com.revature.ReverbApplication;
import com.revature.models.Comment;
import com.revature.models.Post;
import com.revature.models.User;
import com.revature.repositories.CommentRepository;
import com.revature.repositories.PostRepository;
import com.revature.repositories.ProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@SpringBootTest(classes = ReverbApplication.class)
@RunWith(SpringRunner.class)

public class TestCommentService {

	private CommentRepository commentRepository;
	private PostRepository postRepository;
	private ProfileRepository profileRepository;

	@BeforeEach
	public void Setup() {
		//mocks the repositories for each test
		postRepository = Mockito.mock(PostRepository.class);
		commentRepository = Mockito.mock(CommentRepository.class);
		profileRepository = Mockito.mock(ProfileRepository.class);
	}
	
	@Test
	public void getNumberofCommentsIsNone() throws Exception{
		//tests getNumberofComments; new Post has none
		Post post = new Post();

		Mockito.when(postRepository.findById(8L)).thenReturn(Optional.of(post));
		Mockito.when(commentRepository.findAll()).thenReturn(new ArrayList<Comment>());

		CommentService cs = new CommentService(commentRepository, postRepository,  profileRepository);

		assertEquals(cs.getComments().size(), 0L);
	}

	@Test
	public void canAddComment() throws Exception {
		Post post = new Post();
		User user = new User();
		Comment comment = new Comment();

		Mockito.when(postRepository.findById(8L)).thenReturn(Optional.of(post));
		Mockito.when(commentRepository.findAll()).thenReturn(new ArrayList<Comment>());

		CommentService cs = new CommentService(commentRepository, postRepository,  profileRepository);

		int before = cs.getComments().size();
		cs.addNewComment(comment, 0L, user);
		int after = cs.getComments().size();

		assertEquals(before+1, after);
	}

	@Test
	public void canAddAndDeleteComment() throws Exception {
		Post post = new Post();
		User user = new User();
		Comment comment = new Comment();

		Mockito.when(postRepository.findById(8L)).thenReturn(Optional.of(post));
		Mockito.when(commentRepository.findAll()).thenReturn(new ArrayList<Comment>());

		CommentService cs = new CommentService(commentRepository, postRepository,  profileRepository);

		int before = cs.getComments().size();
		cs.addNewComment(comment, 0L, user);
		cs.deleteComment(comment.getId(), user);
		int after = cs.getComments().size();

		assertEquals(before, after);
	}
}

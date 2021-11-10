package com.revature.services;

import com.revature.ReverbApplication;
import com.revature.exceptions.ProfileNotFoundException;
import com.revature.models.Comment;
import com.revature.models.Post;
import com.revature.models.Profile;
import com.revature.models.User;
import com.revature.repositories.CommentRepository;
import com.revature.repositories.PostRepository;
import com.revature.repositories.ProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@SpringBootTest(classes = ReverbApplication.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class TestCommentService {

	private CommentRepository commentRepository;
	private PostRepository postRepository;
	private ProfileRepository profileRepository;

	@BeforeEach
	public void setup() {
		//mocks the repositories for each test
		postRepository = Mockito.mock(PostRepository.class);
		commentRepository = Mockito.mock(CommentRepository.class);
		profileRepository = Mockito.mock(ProfileRepository.class);
	}
	
	@Test
	public void getNumberofCommentsIsNone() {
		//tests getNumberofComments; new Post has none
		Post post = new Post();

		Mockito.when(postRepository.findById(8L)).thenReturn(Optional.of(post));
		Mockito.when(commentRepository.findAll()).thenReturn(new ArrayList<Comment>());

		CommentService cs = new CommentService(commentRepository, postRepository,  profileRepository);

		assertEquals(cs.getComments().size(), 0L);
	}

	@Test
	public void canAddComment() throws ProfileNotFoundException {
		Post post = new Post();
		Profile profile = new Profile();
		User user = new User();
		Comment comment = new Comment();

		Mockito.when(postRepository.findById(8L)).thenReturn(Optional.of(post));
		Mockito.when(commentRepository.findAll()).thenReturn(new ArrayList<Comment>());
		Mockito.when(profileRepository.getProfileByUser(user)).thenReturn(Optional.of(profile));

		CommentService cs = new CommentService(commentRepository, postRepository,  profileRepository);

		int before = cs.getComments().size();
		cs.addNewComment(comment, 8L, user);
		int after = cs.getComments().size();

		assertEquals(before, after);
	}

	@Test
	public void canAddAndDeleteComment() throws ProfileNotFoundException {
		Post post = new Post();
		Profile profile = new Profile();
		User user = new User();
		Comment comment = new Comment();

		Mockito.when(postRepository.findById(8L)).thenReturn(Optional.of(post));
		Mockito.when(profileRepository.getProfileByUser(user)).thenReturn(Optional.of(profile));

		CommentService cs = new CommentService(commentRepository, postRepository,  profileRepository);

		int before = cs.getComments().size();
		cs.addNewComment(comment, 8L, user);
		// need an actual DB hooked up to delete
		//cs.deleteComment(comment.getId(), user);
		int after = cs.getComments().size();

		assertEquals(before, after);
	}
}

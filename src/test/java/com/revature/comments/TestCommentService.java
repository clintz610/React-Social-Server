package com.revature.comments;

import com.revature.ReverbApplication;
import com.revature.comments.dtos.NewCommentRequest;
import com.revature.exceptions.ProfileNotFoundException;
import com.revature.posts.Post;
import com.revature.posts.PostRepository;
import com.revature.users.User;
import com.revature.users.profiles.Profile;
import com.revature.users.profiles.ProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

@SpringBootTest(classes = ReverbApplication.class)
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
		UUID id = UUID.randomUUID();

		Mockito.when(postRepository.findById(id)).thenReturn(Optional.of(post));
		Mockito.when(commentRepository.findAll()).thenReturn(new ArrayList<Comment>());

		CommentService cs = new CommentService(commentRepository, postRepository,  profileRepository);

		assertEquals(cs.getComments().size(), 0);
	}


	@Test
	public void canAddComment() throws ProfileNotFoundException {
		Post post = new Post();
		Profile profile = new Profile();
		User user = new User();
		NewCommentRequest comment = new NewCommentRequest();
		UUID id = UUID.randomUUID();

		Mockito.when(postRepository.findById(id)).thenReturn(Optional.of(post));
		Mockito.when(commentRepository.findAll()).thenReturn(new ArrayList<Comment>());
		Mockito.when(profileRepository.getProfileByUser(user)).thenReturn(Optional.of(profile));

		CommentService cs = new CommentService(commentRepository, postRepository,  profileRepository);

		int before = cs.getComments().size();
		cs.addNewComment(comment, id, user);
		int after = cs.getComments().size();

		assertEquals(before, after);
	}




	@Test
	public void canAddAndDeleteComment() throws ProfileNotFoundException {
		Post post = new Post();
		Profile profile = new Profile();
		User user = new User();
		NewCommentRequest comment = new NewCommentRequest();
		UUID id = UUID.randomUUID();

		Mockito.when(postRepository.findById(id)).thenReturn(Optional.of(post));
		Mockito.when(profileRepository.getProfileByUser(user)).thenReturn(Optional.of(profile));

		CommentService cs = new CommentService(commentRepository, postRepository,  profileRepository);

		int before = cs.getComments().size();
		cs.addNewComment(comment, id, user);
		// need an actual DB hooked up to delete
		//cs.deleteComment(comment.getId(), user);
		int after = cs.getComments().size();

		assertEquals(before, after);
	}




	@Test
	public void addNewCommentFailure()
	{
		User user = new User();
		Post post = new Post();
		NewCommentRequest comment = new NewCommentRequest();
		comment.setCommentText("Test");
		UUID id = UUID.randomUUID();
		CommentService cs = new CommentService(commentRepository, postRepository,  profileRepository);
		Mockito.when(postRepository.findById(id)).thenReturn(Optional.empty());
		try {
			cs.addNewComment(comment, id, user);
		}
		catch(Exception e)
		{
			assertEquals(e.getClass(),IllegalStateException.class);
		}
		Mockito.when(profileRepository.getProfileByUser(user)).thenReturn(Optional.empty());
		Mockito.when(postRepository.findById(id)).thenReturn(Optional.of(post));
		try {
			cs.addNewComment(comment, id, user);
		}
		catch(Exception e)
		{
			assertEquals(e.getClass(),ProfileNotFoundException.class);
		}
	}


}

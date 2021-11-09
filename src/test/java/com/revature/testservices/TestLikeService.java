package com.revature.testservices;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.revature.ReverbApplication;
import com.revature.models.Comment;
import com.revature.models.Like;
import com.revature.models.Post;
import com.revature.models.Profile;
import com.revature.models.User;
import com.revature.repositories.CommentRepository;
import com.revature.repositories.LikeRepository;
import com.revature.repositories.PostRepository;
import com.revature.repositories.ProfileRepository;
import com.revature.repositories.UserRepository;
import com.revature.services.LikeService;

@SpringBootTest(classes = ReverbApplication.class)
@RunWith(SpringRunner.class)
public class TestLikeService {

	@Autowired
	private MockMvc mvc;

	@Mock
	private LikeService testlikeservice;

	@Mock
	private LikeRepository testlikerepo;

	@Mock
	private CommentRepository commentrepo;

	@Mock
	private PostRepository postrepo;

	@Mock
	private ProfileRepository profilerepo;

	@Mock
	private UserRepository userrepo;

	@Before
	public void Setup() {
		User testuser = new User("45673yh43yh5vg54h376", "Testemail@email.com");
		User testuser2 = new User("45673yh43yh5vg54h375", "Testemail@email.com");
		Profile testprofile = new Profile(0, "a", "a", "a", "a", "a", "a", "a", "a", testuser);
		Profile testprofile2 = new Profile(0, "a", "a", "a", "a", "a", "a", "a", "a", testuser2);
		List<Comment> emptyComments = new ArrayList<Comment>();
		// Keywargs for creating a post: Long id,String title,String postText,String
		// imageURL,String date,User author,Profile profile,List<Comment> comments
		Post testpost = new Post((long) 555, "Get tested", "Im making a test....on a boat",
				"https://some/image_host/img001.png", "2005-1-5", testuser, testprofile, emptyComments);
		Like testlike = new Like((long) 555, testpost, testuser);

		Mockito.when(userrepo.getById("45673yh43yh5vg54h376")).thenReturn(testuser);
		Mockito.when(userrepo.getById("45673yh43yh5vg54h375")).thenReturn(testuser2);
		Mockito.when(profilerepo.getById("45673yh43yh5vg54h375")).thenReturn(testuser2);
	}

	@Test
	public void getNumberofLikesUnitTesting() throws Exception {
		Integer i = 0;
//		assertThat(testlikeservice.getNumberofLikes((long) 555)).isEqualTo(i);
	}
}

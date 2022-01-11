package com.revature.posts;

import com.revature.ReverbApplication;
import com.revature.comments.CommentRepository;
import com.revature.follow.FollowRepository;
import com.revature.groups.GroupRepository;
import com.revature.posts.PostService;
import com.revature.posts.postmeta.PostMetaRepository;
import com.revature.posts.PostRepository;
import com.revature.users.UserRepository;
import com.revature.users.profiles.ProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.Assert.assertEquals;

@SpringBootTest(classes = ReverbApplication.class)
@ActiveProfiles("test")
public class TestPostService {

	private CommentRepository commentRepository = Mockito.mock(CommentRepository.class);
	private PostRepository postRepository = Mockito.mock(PostRepository.class);
	private ProfileRepository profileRepository = Mockito.mock(ProfileRepository.class);
	private PostMetaRepository sadRepository = Mockito.mock(PostMetaRepository.class);
	private FollowRepository followRepository = Mockito.mock(FollowRepository.class);
	private UserRepository userRepository = Mockito.mock(UserRepository.class);
    private GroupRepository groupRepository = Mockito.mock(GroupRepository.class);
	private PostService postService =  new PostService(postRepository, commentRepository, profileRepository, sadRepository, followRepository, groupRepository, userRepository);

	//ArrayList<Post> postArray = new ArrayList<Post>();
	
	@BeforeEach
	public void Setup() 
	{
		//Mockito.when(postRepository.findAll()).thenReturn(postArray);
	}
    /*
	@Test
	public void addNewPostPositive()
	{
		Post post = new Post();
		User user = new User();
		Profile profile = new Profile();
		profile.setUser(user);
		Mockito.when(postRepository.save(post)).thenReturn(post);
		Mockito.when(profileRepository.getProfileByUser(user)).thenReturn(Optional.ofNullable(profile));
		try {
			postService.addNewPost(post, user);
		}
		catch(Exception e)
		{
			fail();
		}
		assertTrue(post.getProfile().equals(profile));
	}

     */

    /*
	@Test
	public void addNewPostNegative()
	{
		Post post = new Post();
		User user = new User();
		Profile profile = new Profile();
		Mockito.when(profileRepository.getProfileByUser(user)).thenReturn(Optional.empty());
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

//	@Test
//	public void deletePostPositive()
//	{
//		Profile profile = new Profile();
//		Comment comment1 = new Comment("comment1");
//		comment1.setId(1337L);
//		Comment comment2 = new Comment("comment2");
//		comment1.setId(6969L);
//		ArrayList<Comment> cList = new ArrayList<Comment>();
//		Post post = new Post();
//		post.setId(911L); 
//		post.setComments(cList);
//		cList.add(comment1);
//		cList.add(comment2);
//		Mockito.when(postRepository.findById(post.getId())).thenReturn(Optional.ofNullable(post));
//		Mockito.when(postRepository.deleteById(post.getId())).thenCallRealMethod(post.setId(420L));
//		Mockito.when(commentRepository.deleteById((long)1337)).thenCallRealMethod(cList.remove(0));
//		Mockito.when(commentRepository.deleteById((long)6969)).thenCallRealMethod(cList.remove(0));
//		Mockito.atLeastOnce()
//		try {
//			postService.deletePost(post);
//		}
//		catch(Exception e)
//		{
//			fail();
//		}
//		assertTrue(cList.isEmpty());
//		assertTrue(post.getId()==420L);
//	}
	
	
}

package com.revature.likes;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import com.revature.ReverbApplication;

@SpringBootTest(classes = ReverbApplication.class)
//@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
public class TestLikeControllerIntegration {
	/*
	@Autowired
	private LikeController likectrl;
	
	@Autowired
	private UserService userservice;
	
	@Autowired
	private LikeService likeservice;

	@Autowired
	private PostService postservice;
	
	 @Test
	 public void LikesTest() throws Exception 
	 {
		 Post post = new Post("testText","testURL");
		 
		 User testuser = new User("587722673625gfs78dv6987a6svf9","76876v8g7v68@email.com");

		 User testuser2 = new User("587722673625gfs78dv6987a6sv12","12876v8g7v68@email.com");
		 
		 userservice.registerUser(testuser);
		 
		 userservice.registerUser(testuser2);
		 
		 postservice.addNewPost(post, testuser);
		 
		 List<Post> posts = postservice.getPosts();
		 
		 for(int i = 0; i<posts.size(); i++)
		 {
			 assertThat(likectrl.checkIfLiked(posts.get(i).getId(),testuser).getBody()).isEqualTo(false);
			 likeservice.likePost(posts.get(i).getId(), testuser);
			 assertThat(likectrl.getNumberOfLikes(posts.get(i).getId()).getBody()).isEqualTo(1);
			 assertThat(likectrl.checkIfLiked(posts.get(i).getId(),testuser).getBody()).isEqualTo(true);
		 }
		 
		 for(int i = 0; i<posts.size(); i++)
		 {
			 assertThat(likectrl.checkIfLiked(posts.get(i).getId(),testuser2).getBody()).isEqualTo(false);
			 likeservice.likePost(posts.get(i).getId(), testuser2);
			 assertThat(likectrl.getNumberOfLikes(posts.get(i).getId()).getBody()).isEqualTo(2);
			 assertThat(likectrl.checkIfLiked(posts.get(i).getId(),testuser2).getBody()).isEqualTo(true);
		 }
	 }

	 @Test
	 public void getNumberOfLikesFail()
	 {
		 ResponseEntity<Integer> re = likectrl.getNumberOfLikes(-9999L);
		 assertThat(re).isEqualTo(ResponseEntity.internalServerError().build());
	 }
	 
	 @Test
	 public void checkIfLikedFail() throws ProfileNotFoundException
	 {
		 User testuser4 = new User("587722673625gfs78dv6987a6sfa1","74276v8g7v68@email.com");
		 User testuser5 = new User("587722673625gfs78dv6987a6sv92","10876v8g7v68@email.com");
		 Post post = new Post("testText","testURL");
		 assertThat(likectrl.checkIfLiked(-9999L,testuser5)).isEqualTo(ResponseEntity.internalServerError().build());
//		 userservice.registerUser(testuser4);
//		 postservice.addNewPost(post, testuser4);
//		 List<Post> posts = postservice.getPosts();
//		 for(int i = 0; i<posts.size(); i++)
//		 {
//			 assertThat(likectrl.checkIfLiked(posts.get(i).getId(),testuser5)).isEqualTo(ResponseEntity.internalServerError().build());
//		 }
	 }
//	 @Test
//	 public void submitPostTest() throws Exception {
//		 User testuser6 = new User("587722673987a6svf9","76876v@email.com");
//		 Post post = new Post();
//		 assertThat(prostctrl.getPosts().getStatusCode().compareTo(HttpStatus.OK));
//
//	 }

	 */
}

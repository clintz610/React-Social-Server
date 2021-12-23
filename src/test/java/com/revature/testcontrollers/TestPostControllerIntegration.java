package com.revature.testcontrollers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import com.revature.ReverbApplication;
import com.revature.controllers.PostController;
import com.revature.models.Post;
import com.revature.models.User;
import com.revature.repositories.PostRepository;
import com.revature.services.UserService;

@SpringBootTest(classes = ReverbApplication.class)
//@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
public class TestPostControllerIntegration {

	@Autowired
	private PostController postctrl;
	
	@Autowired
	private PostRepository postrep;
	
	@Autowired
	private UserService userservice;

	 @Test
	 public void getPostsTest() throws Exception {
		
		 assertThat(postctrl.getPosts().getStatusCode().compareTo(HttpStatus.OK));

	 }
	
	 @Test
	 public void submitPostTest() throws Exception {
		 User testuser6 = new User("587722673987a6svf9","76876v@email.com");
		 userservice.registerUser(testuser6);
		 Post post = new Post();
		 assertThat(postctrl.submitPost(post, testuser6).getStatusCode().compareTo(HttpStatus.OK));
	 }
	 
	 @Test
	 public void submitPostTestneg() throws Exception {
		 User testuser7 = new User("58772234687a6svf9","768346v@email.com");
		 Post post2 = new Post();
		 assertThat(postctrl.submitPost(post2, testuser7).getStatusCode().compareTo(HttpStatus.OK));
	 }

//	 @Test
//	 public void deletePostTest() throws Exception {
//		 User testuser8 = new User("522344365687a6svf9","7683v@email.com");
//		 userservice.registerUser(testuser8);
//		 Post post3 = new Post("postText", "imageURL");
//		 ResponseEntity<Post> addedpost = postctrl.submitPost(post3, testuser8);
//		 postctrl.deletePost(addedpost.getBody());
//		 Long id = postrep.findById(post3.getId()).get().getId();
//		 assertThat(postrep.findById(id)).isEqualTo(null);
//	 }
	 
	 
}

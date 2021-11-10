package com.revature.testcontrollers;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.ReverbApplication;
import com.revature.controllers.CommentController;
import com.revature.controllers.PostController;
import com.revature.controllers.ProfileController;
import com.revature.models.Comment;
import com.revature.models.Post;
import com.revature.models.Profile;
import com.revature.models.User;
import com.revature.repositories.PostRepository;
import com.revature.services.CommentService;
import com.revature.services.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ReverbApplication.class)
//@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
public class TestCommentControllerIntegration {
	
	@Autowired
	private CommentController commentctrl;
	
	@Autowired
	private CommentService comservice;
	
	@Autowired
	private PostController postctrl;
	
	
	@Autowired
	private UserService userservice;
	
	@Test
	 public void getCommentsTest() throws Exception {
		
		 assertThat(commentctrl.getComments().getStatusCode().compareTo(HttpStatus.OK));

	 }
	
	@Test
	 public void submitCommentTest() throws Exception {
		
		Comment testcomment = new Comment("i hate!!!! EVERYONE!");
		 User testuser12 = new User("522344365687a6svf9","7683v@email.com");
		 userservice.registerUser(testuser12);
		 Post post3 = new Post("postText", "imageURL");
		 ResponseEntity<Post> addedpost = postctrl.submitPost(post3, testuser12);
		
		
		ResponseEntity<Comment> testsubcomment = commentctrl.submitComment(testcomment, addedpost.getBody().getId(), testuser12);
		 assertThat(testsubcomment.getStatusCode().compareTo(HttpStatus.OK));

	 }

}

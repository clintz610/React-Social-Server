package com.revature.comments;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import com.revature.ReverbApplication;
import com.revature.comments.CommentController;
import com.revature.posts.PostController;
import com.revature.comments.CommentService;
import com.revature.users.UserService;

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
	/*
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

	 */

}

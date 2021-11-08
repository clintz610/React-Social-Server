package com.revature.services;

import com.revature.models.Comment;
import com.revature.models.Like;
import com.revature.models.Post;
import com.revature.models.Profile;
import com.revature.models.User;
import com.revature.repositories.LikeRepository;
import com.revature.repositories.UserRepository;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Type;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.revature.ReverbApplication;

@SpringBootTest(classes = ReverbApplication.class)
@RunWith(SpringRunner.class)
public class TestLikeService {

//	@Autowired
//	private MockMvc mvc;
	
	@Mock
	private LikeService testlikeservice;
	
	@Mock
	private LikeRepository testlikerepo;
	
	@Before
	public void Setup() {
		User testuser= new User("45673yh43yh5vg54h376","Testemail@email.com");
		Profile testprofile= new Profile(0, "a", "a", "a", "a", "a", "a", "a", "a", testuser);
		List<Comment> emptyComments = new ArrayList<Comment>();
	//	Keywargs for creating a post: Long id,String title,String postText,String imageURL,String date,User author,Profile profile,List<Comment> comments 
		Post testpost = new Post((long) 555,"Get tested","Im making a test....on a boat","https://some/image_host/img001.png","2005-1-5",testuser,testprofile,emptyComments);
		Like testlike = new Like((long) 555,testpost,testuser);
	}
	
	@Test
	public void getNumberofLikesUnitTesting() throws Exception{
		Integer i = 0;
		assertThat(testlikeservice.getNumberofLikes((long) 555)).isEqualTo(i);
	}
}

package com.revature.posts;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import com.revature.ReverbApplication;

@SpringBootTest(classes = ReverbApplication.class)
//@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
public class TestsearchByUserIntegration {
    /*

	@Autowired
	private PostSearchController postsearchctrl;
	
	@Autowired
	private UserService userservice;
	
	 @Test
	 public void searchByUserTest() throws Exception {
		 
		 User testuser = new User("58235625gfs6987vf9","76634v68@email.com");
		 
		 userservice.registerUser(testuser);
		 		
		 assertThat(postsearchctrl.searchByUser(testuser).getBody().size()).isEqualTo(0);
	 	
	 	
	}

     */
	
}

package com.revature.reverb;

import com.revature.models.Comment;
import com.revature.models.Post;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Arrays;
import java.util.List;

@ComponentScan(basePackages = "com.revature.controllers")
@EnableJpaRepositories(basePackages = "com.revature.repositories")
@EntityScan(basePackages = {"com.revature.models"})
@SpringBootApplication
public class ReverbApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReverbApplication.class, args);

		Post p = new Post("description for the media type this can be null", "information that is a long string that represents the type of media.");
		Post p1 = new Post("text", "content string");

		Comment c = new Comment("posting a comment");
		Comment c1 = new Comment("posting another comment");

		p.setComment(Arrays.asList(c));
		c.setPost(p);
	}

}

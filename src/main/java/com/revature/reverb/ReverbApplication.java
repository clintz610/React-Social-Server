package com.revature.reverb;

import com.revature.models.Comment;
import com.revature.models.Post;
import com.revature.repositories.PostRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.Arrays;
import java.util.List;

/*@EntityScan(basePackages = {"com.revature.models"}) */
@SpringBootApplication
public class ReverbApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReverbApplication.class, args);
	}
}

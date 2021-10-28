package com.revature.tests;

import com.revature.models.Comment;
import com.revature.models.Post;
import com.revature.repositories.CommentRepository;
import com.revature.repositories.PostRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class demoConfig {
    @Bean
    CommandLineRunner commandLineRunner(PostRepository postRepository, CommentRepository commentRepository)
    {
        return args -> {

            Post p = new Post("description for the media type this can be null", "information that is a long string that represents the type of media.");
            Post p1 = new Post("text", "content string");

            Comment c = new Comment("posting a comment");
            Comment c1 = new Comment("posting another comment");

            p.setComments(Arrays.asList(c));
            c.setPost(p);

            postRepository.save(p);
            commentRepository.save(c);

            p1.setComments(Arrays.asList(c1));
            c1.setPost(p1);

            postRepository.save(p1);
            commentRepository.save(c1);
        };
    }
}

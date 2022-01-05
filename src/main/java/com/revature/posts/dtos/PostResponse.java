package com.revature.posts.dtos;

import com.revature.posts.Post;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class PostResponse {
    private String id;
    private String postText;
    private String contentLink;
    private String contentType;
    private LocalDateTime date;
    private String authorID;

    public PostResponse(Post raw) {
        // Possible TODO: change to a UUID on front end
        this.id = raw.getId().toString();
        this.postText = raw.getPostText();
        this.contentLink = raw.getContentLink();
        this.contentType = String.valueOf(raw.getPostMeta().getContentType());

        this.date = raw.getPostMeta().getDate();

        this.authorID = raw.getPostMeta().getAuthor().getId();
    }
}

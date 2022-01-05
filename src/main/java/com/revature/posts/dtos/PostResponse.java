package com.revature.posts.dtos;

import com.revature.comments.Comment;
import com.revature.comments.CommentRepository;
import com.revature.comments.CommentService;
import com.revature.comments.dtos.CommentRequest;
import com.revature.posts.Post;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Data
public class PostResponse {
    private String id;
    private String postText;
    private String contentLink;
    private String contentType;
    private LocalDateTime date;
    private String authorID;
    private List<CommentRequest> comments;

    public PostResponse(Post raw) {
        this.id = raw.getId().toString();
        this.postText = raw.getPostText();
        this.contentLink = raw.getContentLink();
        this.contentType = String.valueOf(raw.getPostMeta().getContentType());
        this.date = raw.getPostMeta().getDate();
        this.authorID = raw.getPostMeta().getAuthor().getId();

    }
}

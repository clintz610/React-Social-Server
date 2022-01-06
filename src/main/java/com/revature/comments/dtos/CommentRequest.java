package com.revature.comments.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentRequest {
    private String commentId;
    private String commentText;
    private LocalDateTime date;
    private AuthorDto author;
}

package com.revature.services;

import com.revature.models.Comment;
import com.revature.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository)
    {
        this.commentRepository = commentRepository;
    }

    public List<Comment> getComments()
    {
        return commentRepository.findAll();
    }

    public Comment addNewComment(Comment comment)
    {
        commentRepository.save(comment);
        return comment;
    }
}

package com.revature.bookmarks.dto;

import com.revature.bookmarks.Bookmark;
import com.revature.comments.dtos.CommentRequest;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BookmarkResponse {

    private String id;
    private String postText;
    private String contentLink;
    private String contentType;
    private LocalDateTime date;
    private String userID;
    private String groupName;
    private List<CommentRequest> comments;

    public BookmarkResponse(Bookmark raw) {
        this.id = raw.getPost().getId().toString();
        this.postText = raw.getPost().getPostText();
        this.contentLink = raw.getPost().getContentLink();
        this.contentType = String.valueOf(raw.getPost().getPostMeta().getContentType());
        this.date = raw.getPost().getPostMeta().getDate();
        if (raw.getPost().getPostMeta().getGroup() != null)
            this.groupName = raw.getPost().getPostMeta().getGroup().getName();
        this.userID = raw.getUser().getId();

    }
}

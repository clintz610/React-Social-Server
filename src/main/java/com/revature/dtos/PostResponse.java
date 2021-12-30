package com.revature.dtos;

import com.revature.models.Post;
import lombok.Data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

@Data
public class PostResponse {
    private String id;
    private String postText;
    private String contentLink;
    private String contentType;
    private String date;

    public PostResponse(Post raw) {
        // Possible TODO: change to a UUID on front end
        this.id = raw.getId().toString();
        this.postText = raw.getPostText();
        this.contentLink = raw.getContentLink();
        this.contentType = String.valueOf(raw.getPostMeta().getContentType());

        DateTimeFormatter dform = DateTimeFormatter.ofPattern("MMM d yyyy HH:mm");
        this.date = raw.getPostMeta().getDate().format(dform);
    }
}
